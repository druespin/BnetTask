package com.example.bnettask

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class NoConnectionFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alert = AlertDialog.Builder(activity)
            alert.setTitle("Сбой подключения")
            .setMessage("Сеть недоступна. Проверьте настройки сети")
            .setPositiveButton("Обновить данные") {
                    dialog, _ -> dialog.cancel()
            }
        return alert.create()
    }
}