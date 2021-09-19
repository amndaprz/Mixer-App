package com.mobdeve.tan.perez.mixer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mobdeve.tan.perez.mixer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

//    lateinit var binding ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.activity_main)
        val menuFragment = MenuFragment()
        val fm: FragmentManager = supportFragmentManager
        fm.beginTransaction().add(R.id.mainLayout, menuFragment).commit()
    }
}