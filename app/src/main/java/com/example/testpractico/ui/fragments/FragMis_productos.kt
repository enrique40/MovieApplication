package com.example.testpractico.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testpractico.R
import com.example.testpractico.databinding.FragmentMisProductosBinding
import com.example.testpractico.ui.fragments.adapters.AdapterProductos
import com.example.testpractico.utils.onClickProductos


class FragMis_productos : Fragment(), onClickProductos {

    private val adapterProductos: AdapterProductos by lazy { AdapterProductos(requireContext(),  this) }

    private lateinit var _binding: FragmentMisProductosBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMisProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shimmerLayout.startShimmer()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.rvProductos.apply {
                showRecyclerView()
                layoutManager = GridLayoutManager(requireContext(),2)

                adapter = adapterProductos
            }
        }, 5000)

    }
    private fun showRecyclerView() {
        binding.shimmerLayout.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.rvProductos.visibility = View.VISIBLE
    }

    override fun onclick() {
        findNavController().navigate(R.id.nav_detallesProductos)
    }


}