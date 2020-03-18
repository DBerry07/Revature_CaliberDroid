package com.revature.caliberdroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.revature.caliberdroid.MainActivity
import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.btnLogin.setOnClickListener {
            val i:Intent = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        supportActionBar?.title = "Caliber"
        setContentView(binding.root)
    }
}
