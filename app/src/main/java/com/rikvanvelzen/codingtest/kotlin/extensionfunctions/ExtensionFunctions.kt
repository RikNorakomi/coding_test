package com.rikvanvelzen.codingtest.kotlin.extensionfunctions

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.text.DecimalFormat


val formatter = DecimalFormat("0.00")

fun Float.formatToTwoDecimals(): String {

    return formatter.format(this)
}

fun Float.isDecimalValueZero(): Boolean {
    val integer = this.toInt()
    val decimal = this - integer.toDouble()
    return decimal == 0.toDouble()
}


fun Context.showToast(msg: String) {

    val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)

    val v = toast.view.findViewById<View>(android.R.id.message) as TextView
    v.gravity = Gravity.CENTER

    toast.show()
}
