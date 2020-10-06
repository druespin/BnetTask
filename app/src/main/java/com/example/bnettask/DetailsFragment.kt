package com.example.bnettask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bnettask.data.Entry
import com.example.bnettask.date.DateParser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private var entry: Entry? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            details_body.text = entry!!.body
            if (entry!!.da == entry!!.dm)
            {
                details_updated.text = "No updates"
            }
            else {
                details_updated.text = DateParser.parse(entry!!.dm)
            }
        }

        details_cancel_btn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }
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