package app.capstone.bansosadmin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import app.capstone.bansosadmin.R
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

@SuppressLint("SimpleDateFormat")
fun formatDate(date: String): String {
    var formattedDate = ""
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    try {
        val parseDate = sdf.parse(date)
        formattedDate =
            SimpleDateFormat("EEEE, dd MMM").format(parseDate!!) + ", " + formatTime(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return formattedDate
}

@SuppressLint("SimpleDateFormat")
fun formatTime(time: String): String {
    var formattedTime = ""
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    try {
        val date = time.split(" ")
        val parseTime = sdf.parse(date[1])

        formattedTime = SimpleDateFormat("HH.mm").format(parseTime!!)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return formattedTime
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(Date())
}

fun Fragment.loading(state: Boolean) {
    val dialog: AlertDialog
    val alert = AlertDialog.Builder(requireContext())
    dialog = alert.setCancelable(false).create()
    if (state) dialog.show() else dialog.dismiss()
}

fun ImageView.processImage(imageName: Uri?) {
    Glide.with(this).load(imageName)
        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
        .apply(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

        )
        .into(this)
}


