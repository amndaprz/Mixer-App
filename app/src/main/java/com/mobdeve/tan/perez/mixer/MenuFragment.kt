package com.mobdeve.tan.perez.mixer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.mobdeve.tan.perez.mixer.databinding.FragmentMenuBinding
import com.mobdeve.tan.perez.mixer.databinding.FragmentRandomWordInputBinding
import com.mobdeve.tan.perez.mixer.databinding.FragmentDiceRollBinding
//import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        binding.buttonRandomWord.setOnClickListener {

            val randomWord = RandomWordInput()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout,randomWord)
            transaction.commit()
        }

        binding.buttonDice.setOnClickListener {

            val diceRoll = DiceRollFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout,diceRoll)
            transaction.commit()
        }

        binding.button8Ball.setOnClickListener {

            val magicBall = MagicBallFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout,magicBall)
            transaction.commit()
        }

        binding.buttonRandomColor.setOnClickListener {

            val randomColor = RandomColorFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout,randomColor)
            transaction.commit()
        }

        binding.buttonCoinFlip.setOnClickListener {

            val coinFlip = CoinFlipFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout,coinFlip)
            transaction.commit()
        }

        binding.buttonSpinBottle.setOnClickListener {

            val spinBottle = SpinBottleFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.mainLayout, spinBottle)
            transaction.commit()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

