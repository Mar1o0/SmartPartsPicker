package com.vlad.sharaga.network.api

import android.content.Context
import android.graphics.BitmapFactory
import com.vlad.sharaga.R
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.ButtonItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.FeedItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.GameItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.IconTitleItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.SearchItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.TitleItem

interface ApiService {

    suspend fun fetchCityNames(): List<String>

    suspend fun fetchHomeFeed(): List<Item>

}

class ApiServiceImpl(
    private val context: Context
) : ApiService {

    override suspend fun fetchCityNames(): List<String> {
        return listOf(
            "Гродно", "Минск", "Могилев", "Витебск", "Брест", "Гомель"
        )
    }

    override suspend fun fetchHomeFeed(): List<Item> {
        return listOf(
            SearchItem,
            IconTitleItem(
                iconResId = R.drawable.ic_game_title_icon,
                title = "Подберите комплектующие \n" +
                        "под требования игры!"
            ),
            GameItem(
                id = 0,
                preview = BitmapFactory.decodeResource(context.resources, R.drawable.preview_dota_2)
            ),
            GameItem(
                id = 1,
                preview = BitmapFactory.decodeResource(context.resources, R.drawable.preview_cyberpunk)
            ),
            GameItem(
                id = 2,
                preview = BitmapFactory.decodeResource(context.resources, R.drawable.preview_destiny_2)
            ),
            ButtonItem(
                title = "Смотреть все игры"
            ),
            TitleItem(
                title = "Видеокарты"
            ),
            FeedItem(
                id = 0,
                preview = BitmapFactory.decodeResource(context.resources, R.drawable.gigabyte_ge_force_rtx_4060_gaming_oc),
                title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                rating = 5f,
                minPrice = "от 1268,06 р.",
                variants = "39 вариантов"
            ),
        )
    }


}