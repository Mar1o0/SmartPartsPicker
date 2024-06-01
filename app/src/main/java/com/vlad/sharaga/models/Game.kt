package com.vlad.sharaga.models

import com.vlad.sharaga.core.adapter.recycler.fingerprints.GameItem

data class Game(
    val id: Int,
    val name: String,
    val imageUrl: String,
) {
    fun toGameItem(): GameItem {
        return GameItem(
            id = id,
            name = name,
            previewUrl = imageUrl,
        )
    }
}
