package com.prashant.material3_compose_template.commonfunctions

import android.content.Context
import android.widget.Toast
import com.prashant.material3_compose_template.activity.MainActivity

object CommonFunctions {
    fun getStringResource(id: Int) =
        (MainActivity.weakReference.get() as MainActivity).resources.getString(id)

    fun String.showToast() =
        Toast.makeText((MainActivity.weakReference.get() as MainActivity), this, Toast.LENGTH_SHORT)
            .show()

    infix fun String.toastAt(context: Context) = Toast.makeText(context, this, Toast.LENGTH_SHORT)
        .show()
}

