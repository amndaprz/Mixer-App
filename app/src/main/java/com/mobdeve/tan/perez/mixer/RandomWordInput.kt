package com.mobdeve.tan.perez.mixer

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mobdeve.tan.perez.mixer.databinding.FragmentRandomWordBinding
import com.mobdeve.tan.perez.mixer.databinding.FragmentRandomWordInputBinding


class RandomWordInput : Fragment() {

    private lateinit var _binding: FragmentRandomWordInputBinding
    private val binding get() = _binding!!
    private val wordList = mutableListOf("")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRandomWordInputBinding.inflate(inflater, container, false)
        binding.mix.visibility = View.INVISIBLE


        binding.enter.setOnClickListener {

            if (wordList.size < 30) {
                wordList.add(binding.inputRandomWord.text.toString())
            } else if (wordList.size == 30) {
                Toast.makeText(context, "Max of 30 words", Toast.LENGTH_SHORT).show()
            }

            if (wordList.size > 2) {
                binding.mix.visibility = View.VISIBLE
                Toast.makeText(context, binding.inputRandomWord.text.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.mix.setOnClickListener{
            val randomindex = (1 until(wordList.size)).random()

            Toast.makeText(context, wordList[randomindex], Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}