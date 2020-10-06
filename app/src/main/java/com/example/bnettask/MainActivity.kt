package com.example.bnettask

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bnettask.adapter.EntryAdapter
import com.example.bnettask.adapter.ItemClickListener
import com.example.bnettask.data.Entry
import com.example.bnettask.web.ListPresenterImpl
import com.example.bnettask.web.ListView
import com.google.android.material.snackbar.Snackbar.make
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ItemClickListener, ListView {

    private lateinit var adapter: EntryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var prefs: SharedPreferences

    private var sessionId: String = "no-id"
    private val presenter = ListPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        prefs = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        sessionId = prefs.getString("session_id", "no-id")!!

        if (sessionId == "no-id") {
            Log.d("Getting data", "Session Id to be defined")
            presenter.getSessionIdAndData()
        }
        else {
            presenter.getData(sessionId)
        }

        add_entry_btn.setOnClickListener {
            if (sessionId != "no-id") {
                intent = Intent(this, AddEntryActivity::class.java)
                intent.putExtra("session_id", sessionId)
                startActivity(intent)
            }
        }
    }

    private fun initView() {
        adapter = EntryAdapter(ArrayList(), this)
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun showIfNoData() {
        no_data_view.visibility = View.VISIBLE
    }

    override fun saveSessionId(id: String) {
        prefs.edit().putString("session_id", id).apply()
        Log.d("SESSION_ID: ", id)
    }

    override fun showEntries(entries: List<Entry>) {
        adapter.setNewData(entries)
    }

    override fun showError(error: String) {
        make(View(this), error, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    override fun onItemClick(position: Int) {
        val entry = adapter.getEntryList()[position]
        val details = DetailsFragment.newInstance(entry)

        add_entry_btn.visibility = View.GONE

        supportFragmentManager.beginTransaction()
            .add(R.id.details_view, details)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        presenter.getData(sessionId)
    }

    override fun onDestroy() {
        presenter.onStop()
        super.onDestroy()
    }

//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}