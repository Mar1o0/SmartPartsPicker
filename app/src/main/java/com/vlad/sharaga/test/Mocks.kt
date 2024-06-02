package com.vlad.sharaga.test

import com.vlad.sharaga.models.Game

class Mocks {

    val games: List<Game>
        get() = listOf(
            Game(
                id = 1,
                name = "Dota 2",
                imageUrl = "file:///android_asset/preview_dota_2.webp"
            ),
            Game(
                id = 2,
                name = "Cyberpunk 2077",
                imageUrl = "file:///android_asset/preview_cyberpunk.webp"
            ),
            Game(
                id = 3,
                name = "PUBG",
                imageUrl = "file:///android_asset/preview_pubg.webp"
            ),
            Game(
                id = 4,
                name = "CS:GO",
                imageUrl = "file:///android_asset/preview_cs_2.webp"
            ),
            Game(
                id = 5,
                name = "Destiny 2",
                imageUrl = "file:///android_asset/preview_destiny_2.webp"
            ),
        )

}