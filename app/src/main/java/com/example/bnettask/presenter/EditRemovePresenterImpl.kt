package com.example.bnettask.presenter

import com.example.bnettask.web.WebRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EditRemovePresenterImpl(private val editView: EditView) : EditRemovePresenter {

    private var disposables = CompositeDisposable()
    private val api = WebRepo.createApi


    override fun editEntry(sessionId: String, id: String, body: String) {
        disposables.add(
            api.editEntry(sessionId, EDIT_ENTRY, id, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    editView.onEditSaved()
                }, {
                    it.stackTrace
                    editView.showOnError(it.message!!)
                }
                ))
    }

    override fun removeEntry(sessionId: String, id: String) {
        disposables.add(
            api.removeEntry(sessionId, REMOVE_ENTRY, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    editView.onEditRemoved()
                },{
                    it.stackTrace
                    editView.showOnError(it.message!!)
                }
                ))
    }

    override fun getData(sessionId: String) {
        disposables.add(
            api.getEntries(sessionId, GET_ENTRIES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                },
                    {
                        it.stackTrace
                    }))
    }

    override fun onStop() {
        disposables.clear()
    }


    companion object {
        const val EDIT_ENTRY = "edit_entry"
        const val REMOVE_ENTRY = "remove_entry"
        const val GET_ENTRIES = "get_entries"
    }
}