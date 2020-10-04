package com.example.bnettask.web


import android.content.Context
import android.util.Log
import com.example.bnettask.R
import com.example.bnettask.data.NoteResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListPresenterImpl(private val listView: ListView) : ListPresenter {

    private var disposables = CompositeDisposable()
    private val api = WebRepo.createApi

    fun getSessionId() {

        disposables.add(
            api.getSessionId(NEW_SESSION)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                if (it.status == 0) {
                    listView.showError(it.error)
                }
                else if (it.status == 1) {
                    listView.saveSessionId(it.data.sessionId)
                }
            }
            .toObservable()
                .flatMap {
                    getNoteResponse(it.data.sessionId)
                }
                .subscribe({
                    showDataLogic(it)
                },{
                    it.stackTrace
                })
        )
    }

    override fun getData(sessionId: String) {
        disposables.add(
            getNoteResponse(sessionId)
                .doOnNext {
                    showDataLogic(it)
                }
                .subscribe({},
                    { it.stackTrace })
            )
    }

    private fun getNoteResponse(sessionId: String): Observable<NoteResponse> =
        WebRepo.createApi.getNotes(sessionId, GET_ENTRIES)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun showDataLogic(response: NoteResponse) {
        if (response.data.isEmpty()) {
            listView.showIfNoData()
        }
        else {
            listView.showNotes(response.data)
        }
    }

    override fun onStop() {
        disposables.clear()
    }

    companion object {
        const val NEW_SESSION = "new_session"
        const val GET_ENTRIES = "get_entries"
    }
}