package com.example.bnettask.web

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.bnettask.R
import com.example.bnettask.data.Note
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListPresenterImpl(private val eView: ListView) : ListPresenter {

    private var disposables = CompositeDisposable()
    private val api = ApiService.createApi
   // var notes = MutableLiveData<List<Note>>()


    fun getSessionId() = disposables.add(

        api.getSessionId(NEW_SESSION)
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    if (it.status == 0) {
                        eView.showError(it.error)
                    }
                    else if (it.status == 1) {
                        eView.saveSessionId(it.data.sessionId)
                    }
                }
                .subscribe())


    override fun getData(sessionId: String) {

        disposables.add(
            api.getNotes(sessionId, GET_ENTRIES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    eView.showNotes(it.data)
                    //notes.value = response.data
                }, {
                    it.stackTrace
                }))
    }

    override fun onStop() {
        disposables.clear()
    }

    companion object {
        const val NEW_SESSION = "new_session"
        const val GET_ENTRIES = "get_entries"
    }



}