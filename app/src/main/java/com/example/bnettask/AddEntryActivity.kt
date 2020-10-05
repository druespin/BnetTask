package com.example.bnettask

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bnettask.web.AddEntryPresenter
import com.example.bnettask.web.AddEntryPresenterImpl
import kotlinx.android.synthetic.main.activity_add.*

class AddEntryActivity: AppCompatActivity() {

    private val presenter = AddEntryPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val sessionId = intent.getStringExtra("session_id")

        add_entry.setOnClickListener{
            val body = text_input.text.toString()

            if (body.isNotEmpty() && sessionId != null) {
                presenter.addEntry(sessionId, body)
                text_input.text.clear()

                Toast.makeText(this, "New entry added", Toast.LENGTH_LONG).show()
            }
        }

        cancel.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}