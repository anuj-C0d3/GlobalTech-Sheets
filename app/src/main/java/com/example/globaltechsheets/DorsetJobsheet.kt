package com.example.globaltechsheets

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.globaltechsheets.databinding.ActivityDorsetDataBinding
import com.example.globaltechsheets.databinding.ActivityDorsetJobsheetBinding
import kotlin.math.sign

class DorsetJobsheet : AppCompatActivity() {
    private lateinit var binding:ActivityDorsetJobsheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding  = ActivityDorsetJobsheetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.sigcus.isVisible = false
        binding.signaturePad.isVisible = false
        binding.techsignimg.isVisible = false
        binding.donebutton.isVisible = false
        binding.customersignimg.isVisible =false
        binding.customersig.setOnClickListener {
            binding.sigcus.isVisible = true
            binding.signaturePad.clear()
            binding.donebutton.isVisible = true
            binding.signaturePad.isVisible = true
            binding.donebutton.setOnClickListener {
                val signbitmap = binding.signaturePad.transparentSignatureBitmap
                if(signbitmap!=null){
                    binding.customersignimg.setImageBitmap(signbitmap)
                    binding.customersignimg.isVisible = true
                }
                binding.sigcus.isVisible = false
                binding.signaturePad.isVisible = false
                binding.donebutton.isVisible = false
            }
        }
        binding.techniciansig.setOnClickListener {
            binding.sigcus.isVisible = true
            binding.signaturePad.clear()
            binding.donebutton.isVisible = true
            binding.signaturePad.isVisible =true
            binding.donebutton.setOnClickListener {
               val signbitmap = binding.signaturePad.transparentSignatureBitmap
                if(signbitmap!=null){
                    binding.techsignimg.setImageBitmap(signbitmap)
                    binding.techsignimg.isVisible =true
                }
                binding.sigcus.isVisible = false
                binding.donebutton.isVisible = false
                binding.signaturePad.isVisible = false
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}