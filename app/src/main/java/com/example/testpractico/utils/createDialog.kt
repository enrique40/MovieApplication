package com.example.testpractico.utils

import android.app.ProgressDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testpractico.R
import com.google.android.material.bottomsheet.BottomSheetDialog

private lateinit var progressBar: ProgressDialog

fun createDialog(context: Context, texto: String, Subtexto: String) {

    progressBar = ProgressDialog(context)
    progressBar.setCanceledOnTouchOutside(true)
    progressBar.setCancelable(true)
    progressBar.show()
    progressBar.setContentView(R.layout.progress_dialog)
    progressBar.findViewById<TextView>(R.id.passwordDialog).text = Subtexto
    progressBar.findViewById<TextView>(R.id.emailDialog).text = texto
    progressBar.findViewById<Button>(R.id.ButtonDialog).setOnClickListener {
        progressBar.dismiss()
    }

    progressBar.window!!.setBackgroundDrawableResource(android.R.color.transparent)

}

fun Fragment.bottomSheetDialogCompra(){

    val bottomSheetDialog  = BottomSheetDialog(
        requireContext(), R.style.BottomSheetDialogTheme
    )
    val bottomSheetView = LayoutInflater.from(requireContext()).inflate(
        R.layout.modal,
        requireActivity().findViewById(R.id.BottomSheetProdcutos) as LinearLayout?

    )

    bottomSheetView.findViewById<View>(R.id.idCancelar).setOnClickListener {
        bottomSheetDialog.dismiss()
    }

    bottomSheetView.findViewById<View>(R.id.idAceptar).setOnClickListener {

        progressBar = ProgressDialog(context)
        progressBar.setCanceledOnTouchOutside(true)
        progressBar.setCancelable(true)
        progressBar.show()
        progressBar.setContentView(R.layout.alert_compra)
        progressBar.findViewById<TextView>(R.id.textModal).text = "Su compra ha sido exitosa"
        progressBar.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        bottomSheetDialog.dismiss()

        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.dismiss()
            findNavController().navigate(R.id.nav_Misproductos)

        }, 1400)



    }
    bottomSheetDialog.setContentView(bottomSheetView)
    bottomSheetDialog.show()
}
