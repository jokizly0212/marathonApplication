package fi.metropolia.marathon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import fi.metropolia.marathon.model.MarathonDatabase
import fi.metropolia.marathon.model.Request
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val requestDetail = MutableLiveData<Request>()

    fun fetch(requestId: Int) {
        launch {
            val request = MarathonDatabase(getApplication()).requestDao().getRequest(requestId)
            //println("LOLOLO")
            //println(requestId)
            //println(request)
            requestDetail.value = request
        }
    }
}