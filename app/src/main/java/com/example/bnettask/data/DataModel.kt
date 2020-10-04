package com.example.bnettask.data

import com.google.gson.annotations.SerializedName


data class SessionResponse(
    val status: Int,
    val data: Session,
    val error: String) {

    inner class Session(@SerializedName("session") val sessionId: String)
}

data class Note(
    val id: String,
    val body: String,
    val da: String,
    val dm: String
)

data class NoteResponse(
    val status: Int,
    val data: List<Note>,
    val error: String
)

data class AddedNoteResponse(
    val status: Int,
    val data: String,
    val error: String
)
