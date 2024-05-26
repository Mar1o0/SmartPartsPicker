package com.vlad.sharaga.network.api

import android.content.Context
import android.view.Gravity
import com.vlad.sharaga.R
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.ArticleItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.ButtonItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.CategoryItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.GameItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.IconTitleItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.TitleItem
import com.vlad.sharaga.ui.category.CategoryType

interface ApiService {

    suspend fun fetchCityNames(): List<String>

    suspend fun fetchHomeFeed(): List<Item>

    suspend fun fetchCatalogItems(): List<Item>

    suspend fun fetchGames(): List<Item>

    suspend fun fetchCategory(categoryId: String): List<Item>

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
            IconTitleItem(
                iconResId = R.drawable.ic_game_title_icon,
                title = "Подберите комплектующие \n" +
                        "под требования игры!"
            ),
            GameItem(
                id = 0,
                previewUrl = "file:///android_asset/preview_dota_2.webp"
            ),
            GameItem(
                id = 1,
                previewUrl = "file:///android_asset/preview_cyberpunk.webp"
            ),
            GameItem(
                id = 2,
                previewUrl = "file:///android_asset/preview_destiny_2.webp"
            ),
            ButtonItem(
                title = "Смотреть все игры",
                gravity = Gravity.END
            ),
            CategoryItem(
                categoryType = CategoryType.CPU,
                title = "Процессор",
                previewUrl = "file:///android_asset/cpu.png",
            ),
            CategoryItem(
                categoryType = CategoryType.MOTHERBOARD,
                title = "Материнская плата",
                previewUrl = "file:///android_asset/motherboard.png",
            ),
            CategoryItem(
                categoryType = CategoryType.RAM,
                title = "Оперативная память",
                previewUrl = "file:///android_asset/ram.png",
            ),
            CategoryItem(
                categoryType = CategoryType.STORAGE,
                title = "Носители информации",
                previewUrl = "file:///android_asset/storage.png",
            ),
            CategoryItem(
                categoryType = CategoryType.GPU,
                title = "Видеокарта",
                previewUrl = "file:///android_asset/gpu.png",
            ),
            CategoryItem(
                categoryType = CategoryType.POWER_SUPPLY,
                title = "Блок питания",
                previewUrl = "file:///android_asset/power_supply.png",
            ),
            CategoryItem(
                categoryType = CategoryType.CASING,
                title = "Корпус",
                previewUrl = "file:///android_asset/casing.png",
            ),
        )
    }

    override suspend fun fetchCatalogItems(): List<Item> {
        return listOf(
            CategoryItem(
                categoryType = CategoryType.CPU,
                title = "Процессор",
                previewUrl = "file:///android_asset/cpu.png",
            ),
            CategoryItem(
                categoryType = CategoryType.MOTHERBOARD,
                title = "Материнская плата",
                previewUrl = "file:///android_asset/motherboard.png",
            ),
            CategoryItem(
                categoryType = CategoryType.RAM,
                title = "Оперативная память",
                previewUrl = "file:///android_asset/ram.png",
            ),
            CategoryItem(
                categoryType = CategoryType.STORAGE,
                title = "Носители информации",
                previewUrl = "file:///android_asset/storage.png",
            ),
            CategoryItem(
                categoryType = CategoryType.GPU,
                title = "Видеокарта",
                previewUrl = "file:///android_asset/gpu.png",
            ),
            CategoryItem(
                categoryType = CategoryType.POWER_SUPPLY,
                title = "Блок питания",
                previewUrl = "file:///android_asset/power_supply.png",
            ),
            CategoryItem(
                categoryType = CategoryType.CASING,
                title = "Корпус",
                previewUrl = "file:///android_asset/casing.png",
            ),
        )
    }

    override suspend fun fetchGames(): List<Item> {
        return listOf(
            TitleItem(
                title = "Комплектующие для \n" +
                        "популярных игр!"
            ),
            GameItem(
                id = 1,
                previewUrl = "file:///android_asset/preview_dota_2.webp"
            ),
            GameItem(
                id = 2,
                previewUrl = "file:///android_asset/preview_cyberpunk.webp"
            ),
            GameItem(
                id = 3,
                previewUrl = "file:///android_asset/preview_pubg.webp"
            ),
            GameItem(
                id = 4,
                previewUrl = "file:///android_asset/preview_cs_2.webp"
            ),
            GameItem(
                id = 5,
                previewUrl = "file:///android_asset/preview_destiny_2.webp"
            ),
        )
    }

    override suspend fun fetchCategory(categoryId: String): List<Item> {
        return when (CategoryType.fromId(categoryId)) {
            CategoryType.CPU -> listOf(
                ArticleItem(
                    previewUrl = "file:///android_asset/gigabyte.png",
                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1268,06 р.",
                    variants = "39 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/sapphire.png",
                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                    rating = 5f,
                    minPrice = "от 2665,72 р.",
                    variants = "25 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/palit.png",
                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1661,48 р.",
                    variants = "46 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/msi.png",
                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                    rating = 5f,
                    minPrice = "от 1779,90 р.",
                    variants = "115 вариантов",
                ),
            )
            CategoryType.MOTHERBOARD -> listOf(
                ArticleItem(
                    previewUrl = "file:///android_asset/gigabyte.png",
                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1268,06 р.",
                    variants = "39 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/sapphire.png",
                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                    rating = 5f,
                    minPrice = "от 2665,72 р.",
                    variants = "25 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/palit.png",
                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1661,48 р.",
                    variants = "46 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/msi.png",
                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                    rating = 5f,
                    minPrice = "от 1779,90 р.",
                    variants = "115 вариантов",
                ),
            )
            CategoryType.RAM -> listOf(
                ArticleItem(
                    previewUrl = "file:///android_asset/gigabyte.png",
                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1268,06 р.",
                    variants = "39 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/sapphire.png",
                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                    rating = 5f,
                    minPrice = "от 2665,72 р.",
                    variants = "25 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/palit.png",
                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1661,48 р.",
                    variants = "46 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/msi.png",
                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                    rating = 5f,
                    minPrice = "от 1779,90 р.",
                    variants = "115 вариантов",
                ),
            )
            CategoryType.STORAGE -> listOf(
                ArticleItem(
                    previewUrl = "file:///android_asset/gigabyte.png",
                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1268,06 р.",
                    variants = "39 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/sapphire.png",
                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                    rating = 5f,
                    minPrice = "от 2665,72 р.",
                    variants = "25 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/palit.png",
                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1661,48 р.",
                    variants = "46 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/msi.png",
                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                    rating = 5f,
                    minPrice = "от 1779,90 р.",
                    variants = "115 вариантов",
                ),
            )
            CategoryType.GPU -> listOf(
                ArticleItem(
                    previewUrl = "file:///android_asset/gigabyte.png",
                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1268,06 р.",
                    variants = "39 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/sapphire.png",
                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                    rating = 5f,
                    minPrice = "от 2665,72 р.",
                    variants = "25 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/palit.png",
                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1661,48 р.",
                    variants = "46 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/msi.png",
                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                    rating = 5f,
                    minPrice = "от 1779,90 р.",
                    variants = "115 вариантов",
                ),
            )
            CategoryType.POWER_SUPPLY -> listOf(
                ArticleItem(
                    previewUrl = "file:///android_asset/gigabyte.png",
                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1268,06 р.",
                    variants = "39 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/sapphire.png",
                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                    rating = 5f,
                    minPrice = "от 2665,72 р.",
                    variants = "25 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/palit.png",
                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1661,48 р.",
                    variants = "46 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/msi.png",
                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                    rating = 5f,
                    minPrice = "от 1779,90 р.",
                    variants = "115 вариантов",
                ),
            )
            CategoryType.CASING -> listOf(
                ArticleItem(
                    previewUrl = "file:///android_asset/gigabyte.png",
                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1268,06 р.",
                    variants = "39 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/sapphire.png",
                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                    rating = 5f,
                    minPrice = "от 2665,72 р.",
                    variants = "25 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/palit.png",
                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                    rating = 5f,
                    minPrice = "от 1661,48 р.",
                    variants = "46 вариантов",
                ),
                ArticleItem(
                    previewUrl = "file:///android_asset/msi.png",
                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                    rating = 5f,
                    minPrice = "от 1779,90 р.",
                    variants = "115 вариантов",
                ),
            )
        }
    }
}