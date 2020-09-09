package com.example.movieshub.utils

import android.content.Context
import android.widget.Toast
import com.example.movieshub.R
import java.io.IOException

class CustomException constructor(
    var code: ERROR_CODE
): IOException() {

    enum class ERROR_CODE {
        NO_DATA,
        SOMETHING_WENT_WRONG
    }

    fun showErrorMessage(context: Context) {
        if(InternetUtil.isInternetOn()) {
            Toast.makeText(context, context.getString(R.string.network_connection), Toast.LENGTH_LONG)
                .show()
        } else {
            when (code) {
                ERROR_CODE.NO_DATA -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.no_data),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                ERROR_CODE.SOMETHING_WENT_WRONG -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.something_went_wrong),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}

