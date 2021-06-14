package com.sanjay.learning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanjay.learning.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
