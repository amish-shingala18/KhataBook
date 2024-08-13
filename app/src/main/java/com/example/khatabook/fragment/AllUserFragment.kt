package com.example.khatabook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.khatabook.databinding.FragmentAllUserBinding

class AllUserFragment : Fragment() {
    private lateinit var binding: FragmentAllUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentAllUserBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
}