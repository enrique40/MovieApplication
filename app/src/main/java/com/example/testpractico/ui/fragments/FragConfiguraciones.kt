package com.example.testpractico.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testpractico.databinding.FragmentConfiguracionesBinding

class FragConfiguraciones : Fragment(){

    private lateinit var _binding: FragmentConfiguracionesBinding
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfiguracionesBinding.inflate(inflater, container, false)
        return binding.root
    }
}