package com.example.bnettask.web

import com.example.bnettask.data.Entry

interface ListView {

    fun showEntries(entries: List<Entry>)

    fun showIfNoData()

    fun saveSessionId(id: String)

    fun showError(error: String)
}

interface ListPresenter {

    fun getData(sessionId: String)

    fun onStop()
}


interface AddEntryPresenter {

    fun addEntry(sessionId: String, body: String)

    fun onStop()
}