package com.example.core.extensions

import android.graphics.Color
import android.view.View
import android.widget.TextView
import java.util.Random

val colorMap: HashMap<Int, Int> = HashMap()

fun setRandomColorWithPosition(view: View, cardPosition: Int) {
    if (!colorMap.containsKey(cardPosition)) {
        val random = Random()
        val color = Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )
        colorMap[cardPosition] = color
    }
    view.setBackgroundColor(colorMap[cardPosition]!!)
}

fun changeTextRandomColor(textView: TextView) {
    if (textView.tag == null) {
        val random = Random()
        val color = Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )
        textView.setTextColor(color)
        textView.tag = "set_color"
    }
}