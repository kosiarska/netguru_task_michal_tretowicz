package pl.michal.tretowicz.util.extension

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.squareup.picasso.RequestCreator
import java.io.File



/**
 * Created by Michał Trętowicz
 */

fun View.visible(show : Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}


// EDIT TEXT

val EditText.textString: String
    get() = this.text.toString()

/// IMAGE VIEW

fun ImageView.load(path: String, request: (RequestCreator) -> RequestCreator) {
    request(context.picasso.load(path)).into(this)
}

fun ImageView.load(file : File, request: (RequestCreator) -> RequestCreator) {
    request(context.picasso.load(file)).into(this)
}

fun ImageView.load(drawable: Int, request: (RequestCreator) -> RequestCreator) {
    request(context.picasso.load(drawable)).into(this)
}


fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}