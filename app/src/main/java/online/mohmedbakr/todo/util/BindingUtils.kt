package online.mohmedbakr.todo.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Locale


@BindingAdapter("android:setDate")
fun TextView.setDate(date: Long) {
    text = if(date != 0L) formatDate(date) else "No time Specified"

}

fun formatDate(time: Long): String {

    return getTime(time)+"          " + getDate(time)
}

@BindingAdapter("android:setTitle")
fun TextView.setTitle(title: String) {
    text = title
}




fun View.setFadeVisible(visible: Boolean) {
        animate().cancel()
        if (visible) {
            if (visibility == View.GONE)
                fadeIn()
        } else {
            if (visibility == View.VISIBLE)
                fadeOut()
        }

}


fun View.fadeIn() {
    this.animate().alpha(1f).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            this@fadeIn.visibility = View.VISIBLE
        }
    })
}

fun View.fadeOut() {
    this.animate().alpha(0f).setDuration(100).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            this@fadeOut.visibility = View.GONE
        }
    })
}

fun getDate(date: Long): String{
    val dateFormat  = SimpleDateFormat("dd.MMM.yyyy", Locale.US)
    return dateFormat.format(date)
}

fun getTime(time: Long): String{
    val timeFormat = SimpleDateFormat("HH:mm", Locale.US)
    return timeFormat.format(time)
}





















