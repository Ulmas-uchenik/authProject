package com.example.myapplication.data.models


import com.google.gson.annotations.SerializedName

data class NotificationsList(
    @SerializedName("Notifications") val notifications: List<Notification>,
    @SerializedName("Status") val status: String
) {
    data class Notification(
        @SerializedName("Date") val date: String,
        @SerializedName("Id") val id: String,
        @SerializedName("Status") val status: String,
        @SerializedName("Text") val text: String,
        @SerializedName("New") val new: String
    )
}