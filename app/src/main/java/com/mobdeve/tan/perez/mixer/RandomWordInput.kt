package com.mobdeve.tan.perez.mixer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mobdeve.tan.perez.mixer.databinding.FragmentRandomWordInputBinding

class RandomWordInput : Fragment() {
    private lateinit var _binding: FragmentRandomWordInputBinding
    private val binding get() = _binding!!

    private val wordList = mutableListOf<String>()
    private lateinit var animFade: Animation
    private lateinit var animMove: Animation
    private var result: String = "None"
    private var numWords: Int = 0

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRandomWordInputBinding.inflate(inflater, container, false)

        binding.inputRandomWord.visibility = View.VISIBLE
        binding.sub.visibility = View.VISIBLE
        binding.enter.visibility = View.VISIBLE

        binding.backBtn.setOnClickListener {
            val mainMenu = MenuFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout,mainMenu)
            transaction.commit()
        }

        binding.shareBtn.setOnClickListener {
            val intent = Intent()
            val message =
                """
                    I entered $numWords words and got $result!
                    
                    Try Mixer now through this link: dlsu.edu.ph
                """

            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share to "))
        }

        binding.enter.setOnClickListener {
            if (binding.inputRandomWord.text.length > 0) {
                if (wordList.size < 30) {
                    wordList.addAll(binding.inputRandomWord.text.split("\\s".toRegex()).toList())
                    Toast.makeText(
                        context,
                        binding.inputRandomWord.text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.inputRandomWord.text.clear()
                    if (wordList.size > 30) {
                        while (wordList.size > 30) {
                            wordList.removeAt(30)
                        }
                    }
                    numWords = wordList.size
                    binding.wordCnt.setText(numWords.toString())
                } else if (wordList.size >= 30) {
                    binding.inputRandomWord.text.clear()
                    Toast.makeText(
                        context,
                        "You have already inputted 30 words",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.mix.setOnClickListener { generateWord() }

        animFade = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        animMove = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        return binding.root
    }

    private fun generateWord() {
        if (wordList.size < 2) {
            Toast.makeText(context, "Enter more words", Toast.LENGTH_SHORT).show()
        } else {
            binding.inputRandomWord.visibility = View.INVISIBLE
            binding.sub.visibility = View.INVISIBLE
            binding.enter.visibility = View.INVISIBLE

            val randomindex = (0 until(wordList.size)).random()

            binding.word1.startAnimation(animMove)
            binding.word2.startAnimation(animMove)
            binding.word3.startAnimation(animMove)

            binding.word3.setText(binding.word2.text)
            binding.word2.setText(binding.word1.text)
            binding.word1.setText(wordList[randomindex])

            binding.word1.startAnimation(animFade)
            binding.word2.startAnimation(animFade)
            binding.word3.startAnimation(animFade)

            result = wordList[randomindex]
        }
    }
}