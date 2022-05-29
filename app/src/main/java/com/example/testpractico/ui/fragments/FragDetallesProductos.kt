package com.example.testpractico.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testpractico.R
import com.example.testpractico.databinding.FragmentdetallesproductosBinding
import com.example.testpractico.utils.bottomSheetDialogCompra
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FragDetallesProductos : Fragment(), OnMapReadyCallback {

    private lateinit var _binding: FragmentdetallesproductosBinding
    private val binding get() = _binding!!
    private lateinit var map:GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentdetallesproductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding.imgProductos.setImageResource(R.drawable.nike7)

        binding.idComprar.setOnClickListener {
            bottomSheetDialogCompra()
        }
        createFragmet()

    }

    private fun createFragmet() {
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        enableLocation()

    }


    private fun isLocationPermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED



    @SuppressLint("MissingPermission")
    private fun enableLocation(){
        if (!::map.isInitialized) return
        if (isLocationPermission()){
            map.isMyLocationEnabled = true
            map.uiSettings.isZoomControlsEnabled = true
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null){
                        Log.e("TAG", "enableLocation: latitude->${location.latitude}  longuitud->${location.longitude}")
                        lastLocation = location
                        val currentLatLong = LatLng(location.latitude, location.longitude)
                        val marrker = MarkerOptions().position(currentLatLong).title("Usuario")
                        map.addMarker(marrker)
                        map.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(currentLatLong, 18f), 4000, null
                        )
                    }
                }


        }else{
            Log.e("TAG", "enableLocation2: paso else" )
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(requireContext(), "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(requireContext(), "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    override fun onResume() {
        super.onResume()
        if (!::map.isInitialized) return
        if(!isLocationPermission()){
            map.isMyLocationEnabled = false
            Toast.makeText(requireContext(), "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }


}