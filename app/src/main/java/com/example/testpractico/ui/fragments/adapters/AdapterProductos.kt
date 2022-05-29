package com.example.testpractico.ui.fragments.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testpractico.databinding.ItemviewproductosBinding
import com.example.testpractico.utils.onClickProductos
import com.facebook.FacebookSdk.getApplicationContext


class AdapterProductos(
    private val context: Context,
    private var listener : onClickProductos
) : RecyclerView.Adapter<AdapterProductos.ProductosViewHolder>(){

    private var mLastClickTime = System.currentTimeMillis()
    private val CLICK_TIME_INTERVAL: Long = 300


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        val view = ItemviewproductosBinding.inflate(LayoutInflater.from(context), parent, false)

        return ProductosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {

        holder.viewBinding.nameProduct.text = "nike"

        val uri = "@drawable/nike7"
        val imageResource: Int = context.getResources().getIdentifier(uri, null, "com.example.testpractico")
        val imagen = ContextCompat.getDrawable(getApplicationContext(), imageResource)
        holder.viewBinding.imgProductos.setImageDrawable(imagen)

        holder.setListener()
    }

    override fun getItemCount(): Int {
        return 16
    }

    inner class ProductosViewHolder(var viewBinding: ItemviewproductosBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun setListener(){
            with(viewBinding.root){
                setOnClickListener {
                    val now = System.currentTimeMillis()
                    when(now - mLastClickTime < CLICK_TIME_INTERVAL){
                        true -> {
                            return@setOnClickListener
                        }
                        false -> {
                            mLastClickTime = now
                            listener.onclick()
                        }
                    }

                }
            }
        }
    }
}