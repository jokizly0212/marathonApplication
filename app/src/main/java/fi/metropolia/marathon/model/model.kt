package fi.metropolia.marathon.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

@Entity
data class Request(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("startPoint")
    val startPoint: String,
    @SerializedName("endPoint")
    val endPoint: String,
    @SerializedName("image_url")
    val image_url: String
)

@Entity
data class User(
    @PrimaryKey
    val userName: String,
    val age: Int,
    val weight: Double,
    val height: Double,
    val address: String
)

class UserWithRequests(
    @Embedded
    val user: User? = null,

    @Relation(parentColumn = "userName", entityColumn = "transporterName")
    val requestList: List<Request>
)

class Route {
    var distance: Distance? = null
    var duration: Duration? = null
    var endAddress: String? = null
    var endLocation: LatLng? = null
    var startAddress: String? = null
    var startLocation: LatLng? = null

    var points: List<LatLng>? = null
}

class Duration(var text: String, var value: Int)

class Distance(var text: String, var value: Int)