package com.example.bnettask

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bnettask.adapter.EntryAdapter
import com.example.bnettask.adapter.ItemClickListener
import com.example.bnettask.data.Note
import com.example.bnettask.web.ListView
import com.example.bnettask.web.ListPresenterImpl
import com.google.android.material.snackbar.Snackbar


class FirstFragment : Fragment(), ItemClickListener, ListView {

    private val adapter = EntryAdapter(ArrayList(), this)
    private val prefs = activity?.getSharedPreferences(
        getString(R.string.app_name),
        Context.MODE_PRIVATE)

    private val presenter = ListPresenterImpl(this)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter


        val sessionId = prefs!!.getString("session_id", "no-id")
        if (sessionId == "no-id") {
            presenter.getSessionId()
        }
        presenter.getData(sessionId!!)

    }

    override fun showIfNoData() {
        Snackbar.make(requireView(), "No data yet", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }


    override fun saveSessionId(id: String) {
        prefs!!.edit().putString("session_id", id).apply()
    }

    override fun showNotes(notes: List<Note>) {
       adapter.setNewData(notes)
    }

    override fun showError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}