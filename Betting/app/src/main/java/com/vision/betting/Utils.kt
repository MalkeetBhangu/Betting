package com.vision.betting

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent

class Utils {

    companion object{
        fun showOkAlert(context: Context, message: String){
            val builder = AlertDialog.Builder(context)
            builder.setMessage(message)
            builder.setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()

                })
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}