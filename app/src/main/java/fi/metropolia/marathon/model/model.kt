package fi.metropolia.marathon.model

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