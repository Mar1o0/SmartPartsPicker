package com.vlad.sharaga.network.api

import android.content.Context
import com.vlad.sharaga.core.adapter.Item
import com.vlad.sharaga.core.adapter.recycler.fingerprints.CategoryItem
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.models.Game
import com.vlad.sharaga.models.Product
import com.vlad.sharaga.models.ProductImage
import com.vlad.sharaga.models.ProductPrice
import com.vlad.sharaga.models.ProductSpec
import com.vlad.sharaga.models.ProductType

interface ApiService {

    suspend fun fetchCityNames(): List<String>?
    suspend fun fetchGames(): List<Game>?
    suspend fun fetchProduct(productId: ProductId): Product?
    suspend fun fetchProducts(productType: String): List<Product>?
    suspend fun fetchProductPrices(productId: ProductId): List<ProductPrice>?
    suspend fun fetchProductImages(productId: ProductId): List<ProductImage>?
    suspend fun fetchProductSpecs(productId: ProductId): List<ProductSpec>?
    suspend fun fetchProductImage(productId: ProductId): ProductImage?
}

class ApiServiceImpl(
    private val context: Context
) : ApiService {

    override suspend fun fetchProductPrices(productId: ProductId): List<ProductPrice> {
        return listOf(
            ProductPrice(
                id = 1,
                productId = 192384,
                shopName = "Sigma",
                url = "https://sigma.by",
                imageUrl = "file:///android_asset/sigma.png",
                price = 2665.72,
                currency = "BYN",
            ),
        )
    }

    override suspend fun fetchProductImages(productId: ProductId): List<ProductImage>? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchProductSpecs(productId: ProductId): List<ProductSpec>? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchProductImage(productId: ProductId): ProductImage? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCityNames(): List<String> {
        return listOf(
            "Гродно", "Минск", "Могилев", "Витебск", "Брест", "Гомель"
        )
    }

    override suspend fun fetchGames(): List<Game>? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchProduct(productId: ProductId): Product? {
        TODO("Not yet implemented")
    }

    override suspend fun fetchProducts(productType: String): List<Product>? {
        TODO("Not yet implemented")
    }

//    override suspend fun fetchHomeFeed(): List<Item> {
//        return listOf(
//            IconTitleItem(
//                iconResId = R.drawable.ic_game_title_icon,
//                title = "Подберите комплектующие \n" +
//                        "под требования игры!"
//            ),
//            GameItem(
//                id = 0,
//                previewUrl = "file:///android_asset/preview_dota_2.webp"
//            ),
//            GameItem(
//                id = 1,
//                previewUrl = "file:///android_asset/preview_cyberpunk.webp"
//            ),
//            GameItem(
//                id = 2,
//                previewUrl = "file:///android_asset/preview_destiny_2.webp"
//            ),
//            ButtonItem(
//                title = "Смотреть все игры",
//                gravity = Gravity.END
//            ),
//            CategoryItem(
//                categoryType = CategoryType.CPU,
//                title = "Процессор",
//                previewUrl = "file:///android_asset/cpu.png",
//            ),
//            CategoryItem(
//                categoryType = CategoryType.MOTHERBOARD,
//                title = "Материнская плата",
//                previewUrl = "file:///android_asset/motherboard.png",
//            ),
//            CategoryItem(
//                categoryType = CategoryType.RAM,
//                title = "Оперативная память",
//                previewUrl = "file:///android_asset/ram.png",
//            ),
//            CategoryItem(
//                categoryType = CategoryType.STORAGE,
//                title = "Носители информации",
//                previewUrl = "file:///android_asset/storage.png",
//            ),
//            CategoryItem(
//                categoryType = CategoryType.GPU,
//                title = "Видеокарта",
//                previewUrl = "file:///android_asset/gpu.png",
//            ),
//            CategoryItem(
//                categoryType = CategoryType.POWER_SUPPLY,
//                title = "Блок питания",
//                previewUrl = "file:///android_asset/power_supply.png",
//            ),
//            CategoryItem(
//                categoryType = CategoryType.CASING,
//                title = "Корпус",
//                previewUrl = "file:///android_asset/casing.png",
//            ),
//        )
//    }

//    override suspend fun fetchCatalogItems(): List<Item> {
//        return listOf(
//            CategoryItem(
//                productType = ProductType.CPU,
//                title = "Процессор",
//                previewUrl = "file:///android_asset/cpu.png",
//            ),
//            CategoryItem(
//                productType = ProductType.MOTHERBOARD,
//                title = "Материнская плата",
//                previewUrl = "file:///android_asset/motherboard.png",
//            ),
//            CategoryItem(
//                productType = ProductType.RAM,
//                title = "Оперативная память",
//                previewUrl = "file:///android_asset/ram.png",
//            ),
//            CategoryItem(
//                productType = ProductType.STORAGE,
//                title = "Носители информации",
//                previewUrl = "file:///android_asset/storage.png",
//            ),
//            CategoryItem(
//                productType = ProductType.GPU,
//                title = "Видеокарта",
//                previewUrl = "file:///android_asset/gpu.png",
//            ),
//            CategoryItem(
//                productType = ProductType.POWER_SUPPLY,
//                title = "Блок питания",
//                previewUrl = "file:///android_asset/power_supply.png",
//            ),
//            CategoryItem(
//                productType = ProductType.CASING,
//                title = "Корпус",
//                previewUrl = "file:///android_asset/casing.png",
//            ),
//        )
//    }
//
//    override suspend fun fetchGames(): List<Game> {
//        return listOf(
//            Game(
//                id = 1,
//                name = "Dota 2",
//                imageUrl = "file:///android_asset/preview_dota_2.webp"
//            ),
//            Game(
//                id = 2,
//                name = "Cyberpunk 2077",
//                imageUrl = "file:///android_asset/preview_cyberpunk.webp"
//            ),
//            Game(
//                id = 3,
//                name = "PUBG",
//                imageUrl = "file:///android_asset/preview_pubg.webp"
//            ),
//            Game(
//                id = 4,
//                name = "CS:GO",
//                imageUrl = "file:///android_asset/preview_cs_2.webp"
//            ),
//            Game(
//                id = 5,
//                name = "Destiny 2",
//                imageUrl = "file:///android_asset/preview_destiny_2.webp"
//            ),
//        )
//    }
//
//    override suspend fun fetchCategory(categoryId: String): List<Item> {
//        return when (ProductType.fromId(categoryId)) {
//            ProductType.CPU -> listOf(
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/gigabyte.png",
//                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1268,06 р.",
//                    variants = "39 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/sapphire.png",
//                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//                    rating = 5f,
//                    minPrice = "от 2665,72 р.",
//                    variants = "25 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/palit.png",
//                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 5f,
//                    minPrice = "от 1661,48 р.",
//                    variants = "46 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/msi.png",
//                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
//                    rating = 5f,
//                    minPrice = "от 1779,90 р.",
//                    variants = "115 вариантов",
//                ),
//            )
//
//            ProductType.MOTHERBOARD -> listOf(
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/gigabyte.png",
//                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1268,06 р.",
//                    variants = "39 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/sapphire.png",
//                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//                    rating = 5f,
//                    minPrice = "от 2665,72 р.",
//                    variants = "25 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/palit.png",
//                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 5f,
//                    minPrice = "от 1661,48 р.",
//                    variants = "46 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/msi.png",
//                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
//                    rating = 5f,
//                    minPrice = "от 1779,90 р.",
//                    variants = "115 вариантов",
//                ),
//            )
//
//            ProductType.RAM -> listOf(
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/gigabyte.png",
//                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1268,06 р.",
//                    variants = "39 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/sapphire.png",
//                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//                    rating = 5f,
//                    minPrice = "от 2665,72 р.",
//                    variants = "25 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/palit.png",
//                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 5f,
//                    minPrice = "от 1661,48 р.",
//                    variants = "46 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/msi.png",
//                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
//                    rating = 5f,
//                    minPrice = "от 1779,90 р.",
//                    variants = "115 вариантов",
//                ),
//            )
//
//            ProductType.STORAGE -> listOf(
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/gigabyte.png",
//                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1268,06 р.",
//                    variants = "39 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/sapphire.png",
//                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//                    rating = 5f,
//                    minPrice = "от 2665,72 р.",
//                    variants = "25 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/palit.png",
//                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 5f,
//                    minPrice = "от 1661,48 р.",
//                    variants = "46 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/msi.png",
//                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
//                    rating = 5f,
//                    minPrice = "от 1779,90 р.",
//                    variants = "115 вариантов",
//                ),
//            )
//
//            ProductType.GPU -> listOf(
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/gigabyte.png",
//                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1268,06 р.",
//                    variants = "39 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/sapphire.png",
//                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//                    rating = 5f,
//                    minPrice = "от 2665,72 р.",
//                    variants = "25 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/palit.png",
//                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1661,48 р.",
//                    variants = "46 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/msi.png",
//                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
//                    rating = 3.4f,
//                    minPrice = "от 1779,90 р.",
//                    variants = "115 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/gigabyte.png",
//                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1268,06 р.",
//                    variants = "39 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/sapphire.png",
//                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//                    rating = 5f,
//                    minPrice = "от 2665,72 р.",
//                    variants = "25 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/palit.png",
//                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1661,48 р.",
//                    variants = "46 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/msi.png",
//                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
//                    rating = 3.4f,
//                    minPrice = "от 1779,90 р.",
//                    variants = "115 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/gigabyte.png",
//                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1268,06 р.",
//                    variants = "39 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/sapphire.png",
//                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//                    rating = 5f,
//                    minPrice = "от 2665,72 р.",
//                    variants = "25 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/palit.png",
//                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1661,48 р.",
//                    variants = "46 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/msi.png",
//                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
//                    rating = 3.4f,
//                    minPrice = "от 1779,90 р.",
//                    variants = "115 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/gigabyte.png",
//                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1268,06 р.",
//                    variants = "39 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/sapphire.png",
//                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//                    rating = 5f,
//                    minPrice = "от 2665,72 р.",
//                    variants = "25 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/palit.png",
//                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1661,48 р.",
//                    variants = "46 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/msi.png",
//                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
//                    rating = 3.4f,
//                    minPrice = "от 1779,90 р.",
//                    variants = "115 вариантов",
//                ),
//            )
//
//            ProductType.POWER_SUPPLY -> listOf(
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/gigabyte.png",
//                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1268,06 р.",
//                    variants = "39 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/sapphire.png",
//                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//                    rating = 3.4f,
//                    minPrice = "от 2665,72 р.",
//                    variants = "25 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/palit.png",
//                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1661,48 р.",
//                    variants = "46 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/msi.png",
//                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
//                    rating = 3.4f,
//                    minPrice = "от 1779,90 р.",
//                    variants = "115 вариантов",
//                ),
//            )
//
//            ProductType.CASING -> listOf(
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/gigabyte.png",
//                    title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1268,06 р.",
//                    variants = "39 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/sapphire.png",
//                    title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//                    rating = 3.4f,
//                    minPrice = "от 2665,72 р.",
//                    variants = "25 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/palit.png",
//                    title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//                    rating = 3.4f,
//                    minPrice = "от 1661,48 р.",
//                    variants = "46 вариантов",
//                ),
//                ArticleItem(
//                    productId = 192384,
//                    previewUrl = "file:///android_asset/msi.png",
//                    title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
//                    rating = 3.4f,
//                    minPrice = "от 1779,90 р.",
//                    variants = "115 вариантов",
//                ),
//            )
//        }
//    }
//
//    override suspend fun fetchArticle(articleId: String): ArticleItem {
//        return ArticleItem(
//            productId = 192384,
//            previewUrl = "file:///android_asset/gigabyte.png",
//            title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GDPalit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
//            rating = 3.4f,
//            minPrice = "от 1268,06 р.",
//            variants = "39 вариантов",
//        )
//    }

//    override suspend fun getArticle(productId: ProductId?): ProductDescription? {
//        return ProductDescription(
//            id = 192384,
//            title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
//            description = "Видеокарта Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G - это мощное графическое устройство с поддержкой трассировки лучей, идеально подходящее для игр в 4K.\nС поддержкой DirectX 12 Ultimate, эта видеокарта обеспечивает высокую производительность и качество графики.\nС разогнанной версией графического процессора и подсветкой, она предлагает улучшенный игровой опыт.\nС 16 ГБ видеопамяти и 80 RT-ядрами, эта видеокарта обеспечивает высокую производительность и плавную работу в самых требовательных играх.",
//            imageUrls = listOf(
//                "file:///android_asset/sapphire.png",
//                "file:///android_asset/gigabyte.png",
//                "file:///android_asset/palit.png",
//                "file:///android_asset/msi.png",
//            ),
//            minPrice = 2665.72,
//            variantsCount = 39,
//            rating = 3.14f,
//            specs = mapOf(
//                "Подбор в один клик" to "с трассировкой лучей, для игр в 4K",
//                "Интерфейс" to "PCI Express x16 4.0",
//                "Производитель графического процессора" to "AMD",
//                "Микроархитектура" to "AMD RDNA 3.0",
//                "Кодовое имя чипа" to "Navi 31",
//                "«Разогнанная» версия" to "true",
//                "Внешняя видеокарта" to "false",
//            )
//        )
//    }
}