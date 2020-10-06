package com.example.bnettask.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class SessionResponse(
    val status: Int,
    val data: Session,
    val error: String) {

    inner class Session(@SerializedName("session") val sessionId: String)
}

@Parcelize
data class Entry(
    val id: String,
    val body: String,
    val da: String,
    val dm: String
) : Parcelable

data class EntryResponse(
    val status: Int,
    val data: List<List<Entry>>,
    val error: String
)

data class AddedNoteResponse(
    val status: Int,
    val data: String,
    val error: String
)
