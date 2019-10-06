package fi.metropolia.marathon.view

import android.view.View

interface RequestClickListener {
    fun onRequestClick(requestId: Int, v: View)
}