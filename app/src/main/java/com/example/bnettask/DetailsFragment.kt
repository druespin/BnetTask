package com.example.bnettask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.bnettask.data.Entry
import com.example.bnettask.presenter.EditRemovePresenterImpl
import com.example.bnettask.presenter.EditView
import com.example.bnettask.util.DateParser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(), EditView {

    private var entry: Entry? = null
    private val editPresenter = EditRemovePresenterImpl(this)

    private lateinit var prefs: SharedPreferences
    private lateinit var sessionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = activity?.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)!!
        sessionId = prefs.getString("session_id", "no-id")!!

        arguments?.let {
            entry = it.getParcelable(ENTRY_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (entry != null) {
            details_created.text = DateParser.parse(entry!!.da)
            details_body.text.clear()
            details_body.text.insert(0, entry!!.body)

            if (entry!!.da == entry!!.dm)
            {
                details_updated.text = "No updates"
            }
            else {
                details_updated.text = DateParser.parse(entry!!.dm)
            }
        }

        details_body.addTextChangedListener {
            details_edit_btn.isEnabled = true
        }

        /**
         *  Save edited entry
         */
        details_edit_btn.setOnClickListener {

            if (entry != null && sessionId != "no-id") {
                val body = details_body.text.toString()
                editPresenter.editEntry(sessionId, entry!!.id, body)
                editPresenter.getData(sessionId)
            }
            closeFragment()
        }

        /**
         *  Remove entry
         */
        details_remove_btn.setOnClickListener {

            if (sessionId != "no-id") {
                editPresenter.removeEntry(sessionId, entry!!.id)
                editPresenter.getData(sessionId)
            }
            closeFragment()
        }

        /**
         *  Close details view
         */
        details_cancel_btn.setOnClickListener {
            closeFragment()
        }
    }

    private fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }

    override fun showOnError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    override fun onEditSaved() {
        Toast.makeText(activity, "Entry saved successfully", Toast.LENGTH_LONG).show()
    }

    override fun onEditRemoved() {
        Toast.makeText(activity, "Entry removed successfully", Toast.LENGTH_LONG).show()
    }


    override fun onDestroy() {
        activity?.add_entry_btn?.visibility = View.VISIBLE
        super.onDestroy()
    }

    companion object {
        const val ENTRY_DATA = "entry_data"

        fun newInstance (entry: Entry): DetailsFragment = DetailsFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelable(ENTRY_DATA, entry)
                }
            }
    }


}

