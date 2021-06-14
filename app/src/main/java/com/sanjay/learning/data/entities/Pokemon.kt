package com.sanjay.learning.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Here the page needs to be var because we want assign page values in repository
 * and store list based on page in the room
 * */
@Entity
data class Pokemon(@PrimaryKey val name: String, var page: Int = 0, val url: String) {

    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "$IMAGE_LOCATION$index.png"
    }

    companion object {
        private const val IMAGE_LOCATION = "https://pokeres.bastionbot.org/images/pokemon/"
    }
}




