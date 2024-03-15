package com.example.thenest_maintrial.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(content: String) {

    Toast.makeText(context, content, Toast.LENGTH_LONG).show()
}
