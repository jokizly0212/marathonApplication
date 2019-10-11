package fi.metropolia.marathon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import fi.metropolia.marathon.model.MarathonDatabase

import fi.metropolia.marathon.model.User
import kotlinx.coroutines.launch

/*View model for user register screen*/

class RegisterViewModel(application: Application): BaseViewModel(application) {
    private val userDao = MarathonDatabase(getApplication()).userDao()

    fun registerUser(user: User) {
        launch {
            val result = userDao.insertUser(user)
        }
    }
}