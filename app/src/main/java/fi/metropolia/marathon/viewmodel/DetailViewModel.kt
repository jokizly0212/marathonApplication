package fi.metropolia.marathon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import fi.metropolia.marathon.model.MarathonDatabase
import fi.metropolia.marathon.model.Request
import kotlinx.coroutines.launch

/*View model for detail of event screen*/
class DetailViewModel(application: Application) : BaseViewModel(application) {
    val requestDetail = MutableLiveData<Request>()

    fun fetch(requestId: Int) {
        launch {
            val request = MarathonDatabase(getApplication()).requestDao().getRequest(requestId)

            requestDetail.value = request
        }
    }
}