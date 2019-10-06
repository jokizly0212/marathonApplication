package fi.metropolia.marathon.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Request(
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