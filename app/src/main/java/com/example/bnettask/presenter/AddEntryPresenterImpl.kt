package com.example.bnettask.presenter

import android.util.Log
import com.example.bnettask.web.WebRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddEntryPresenterImpl : AddEntryPresenter {

    private var disposables = CompositeDisposable()
    private val api = WebRepo.createApi

        override fun addEntry(sessionId: String, body: String) {
            disposables.add(
                api.addEntry(sessionId, ADD_ENTRY, body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("ADD_ENTRY request sent", body)
                    }, {
                        it.stackTrace
                    })
            )
        }

    override fun onStop() {
        disposables.clear()
    }

    companion object {
        const val ADD_ENTRY = "add_entry"
    }
}