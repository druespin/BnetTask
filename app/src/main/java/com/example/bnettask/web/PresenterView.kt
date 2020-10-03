package com.example.bnettask.web

import com.example.bnettask.data.Note

interface ListView {

    fun showNotes(notes: List<Note>)

    fun showIfNoData()

    fun saveSessionId(id: String)

    fun showError(error: String)
}

interface ListPresenter {

    fun getData(sessionId: String)

    fun onStop()
}