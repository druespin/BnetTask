package com.example.bnettask.date

import java.sql.Date

class DateParser {

    companion object {

        fun parse(seconds: String) = Date(seconds.toLong() * 1000).toString()
    }
}