package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.example.chatapp.databinding.ActivityUserNameBinding

class UserNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activation du bouton après saisie du nom d'utilisateur.
        binding.etUsername.doAfterTextChanged {
            binding.btnProceed.isEnabled = it.toString().isNotEmpty()
        }

        binding.btnProceed.setOnClickListener {
            val username = binding.etUsername.text.toString()
            if (username.isNotEmpty()) {
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra(ChatActivity.USERNAME, username) // Chaîne de caractères
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etUsername.requestFocus() // Focus sur le champ de saisie.
    }
}
