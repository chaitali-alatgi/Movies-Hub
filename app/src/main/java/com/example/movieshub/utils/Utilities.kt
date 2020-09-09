package com.example.movieshub.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.movieshub.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat

fun formatDecimalString(data: Double, pattern: String): String {
    val df = DecimalFormat(pattern)
    df.roundingMode = RoundingMode.CEILING
    return df.format(data)
}

fun formatDate(dateString: String?, fromDateFormat: String, toDateFormat: String): String {
    if(dateString.isNullOrEmpty()) {
        return ""
    }
    val fromSimpleDateformatter: SimpleDateFormat = SimpleDateFormat(fromDateFormat)
    var fromDate = fromSimpleDateformatter.parse(dateString)
    var toDateSimpleDateFormater: SimpleDateFormat = SimpleDateFormat(toDateFormat)
    var toDate = toDateSimpleDateFormater.format(fromDate!!).toString()
    return toDate
}

fun ratingsView(rating: Double, linearLayout: LinearLayout, context: Context) {
    linearLayout.removeAllViews()
    var ratingValue = rating.div(2)
    if(ratingValue <= 0) {
        linearLayout.visibility = View.GONE
    } else {
        val ratingString = formatDecimalString(ratingValue, "#.#")
        for(i in 0..ratingString.split('.')[0].toInt()) {
            linearLayout.addView(addImage(false, context))
        }
        if(ratingString.contains('.') && ratingString.split('.')[1].toInt() > 5) {
            linearLayout.addView(addImage(true, context))
        }
        linearLayout.visibility = View.VISIBLE
    }

}

private fun addImage(isPointFive: Boolean, context: Context): ImageView {
    var starImage = ImageView(context)
    var params = ViewGroup.LayoutParams(
        context.resources.getDimension(R.dimen.twenty).toInt(),
        context.resources.getDimension(R.dimen.twenty).toInt()
    )
    starImage.layoutParams = params
    if(isPointFive) {
        starImage.setImageResource(R.drawable.half_star)
    } else {
        starImage.setImageResource(R.drawable.star)
    }
    return starImage
}