package fi.metropolia.marathon.view

import android.view.View

interface EventClickListener {
    fun onRequestClick(requestId: Int, v: View)
}