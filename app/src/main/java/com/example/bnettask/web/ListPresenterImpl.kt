package com.example.bnettask.web


import com.example.bnettask.data.Entry
import com.example.bnettask.data.EntryResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListPresenterImpl(private val listView: ListView) : ListPresenter {

    private var disposables = CompositeDisposable()
    private val api = WebRepo.createApi

    fun getSessionIdAndData() {

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
                .flatMap {
                    getNoteResponse(it.data.sessionId)
                }
                .subscribe({
                    showDataLogic(it.data[0])
                },{
                    it.stackTrace
                })
        )
    }

    override fun getData(sessionId: String) {
        disposables.add(
            api.getEntries(sessionId, GET_ENTRIES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showDataLogic(it.data[0])
                },
                    {
                        it.stackTrace
                    }))
    }

    private fun getNoteResponse(sessionId: String): Single<EntryResponse> =
        api.getEntries(sessionId, GET_ENTRIES)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun showDataLogic(entries: List<Entry>) {
        if (entries.isEmpty()) {
            listView.showIfNoData()
        }
        else {
            listView.showEntries(entries)
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