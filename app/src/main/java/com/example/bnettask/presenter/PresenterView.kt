package com.example.bnettask.presenter

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

interface EditView {

    fun onEditSaved()

    fun onEditRemoved()

    fun showOnError(error: String)
}

interface EditRemovePresenter {

    fun editEntry(sessionId: String, id: String, body: String)

    fun removeEntry(sessionId: String, id: String)

    fun getData(sessionId: String)

    fun onStop()
}