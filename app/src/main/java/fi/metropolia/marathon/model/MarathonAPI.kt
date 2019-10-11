package fi.metropolia.marathon.model

import io.reactivex.Single
import retrofit2.http.GET
/* Use API server */

interface RequestsApi {
    @GET("tranhuyviet/marathonAPI/master/marathonAPI.json")
    fun getRequests(): Single<List<Request>>
}