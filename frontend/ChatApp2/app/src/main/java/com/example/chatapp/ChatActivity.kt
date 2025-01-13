package com.example.chatapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityMainBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var socketHandler: SocketHandler
    private lateinit var binding: ActivityMainBinding
    private lateinit var chatAdapter: ChatAdapter

    private val chatList = mutableListOf<Chat>() // Liste des messages.

    private var userName = "" // Nom de l'utilisateur courant.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra(USERNAME) ?: "" // Récupération du nom d'utilisateur.

        if (userName.isEmpty()) {
            finish() // Fermeture si aucun nom d'utilisateur fourni.
        } else {
            socketHandler = SocketHandler()
            chatAdapter = ChatAdapter()

            // Configuration de la RecyclerView pour afficher les messages.
            binding.rvChat.apply {
                layoutManager = LinearLayoutManager(this@ChatActivity)
                adapter = chatAdapter
            }

            // Envoi d'un message via le socket.
            binding.btnSend.setOnClickListener {
                val message = binding.etMsg.text.toString()
                if (message.isNotEmpty()) {
                    val chat = Chat(username = userName, text = message)
                    socketHandler.emitChat(chat)
                    binding.etMsg.setText("")
                }
            }

            // Observation des nouveaux messages reçus.
            socketHandler.onNewChat.observe(this) {
                val chat = it.copy(isSelf = it.username == userName)
                chatList.add(chat)
                chatAdapter.submitChat(chatList)
                binding.rvChat.scrollToPosition(chatList.size - 1)
            }
        }
    }

    override fun onDestroy() {
        socketHandler.disconnectSocket() // Déconnexion du socket à la fermeture de l'activité.
        super.onDestroy()
    }

    companion object {
        const val USERNAME = "username"
    }
}