package com.example.thenest_maintrial

import android.app.Dialog
import android.content.Context
import android.os.Bundle

class LoadingDialog(context: Context) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}