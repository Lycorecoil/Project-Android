package com.example.chatapp

import androidx.room.Entity
import androidx.room.PrimaryKey

// Définition de l'entité Room pour stocker les messages dans une base de données locale.
@Entity(tableName = "chat")
data class Chat(
    @PrimaryKey(autoGenerate = true) // Génération automatique de l'ID unique.
    var id: Int = 0,
    val username: String, // Nom d'utilisateur associé au message.
    val text: String, // Contenu du message.
    var isSelf: Boolean = false // Indique si le message provient de l'utilisateur local.
)