package fi.metropolia.marathon.view

import android.view.View

/* Listener interface for event click*/
interface EventClickListener {
    fun onRequestClick(requestId: Int, v: View)
}