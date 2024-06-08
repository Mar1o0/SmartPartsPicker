package com.vlad.sharaga.test

import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.models.Filter
import com.vlad.sharaga.models.FilterType
import com.vlad.sharaga.models.Product
import com.vlad.sharaga.models.ProductImage
import com.vlad.sharaga.models.ProductPrice
import com.vlad.sharaga.models.ProductSpec
import com.vlad.sharaga.models.ProductType
import com.vlad.sharaga.network.api.ApiService

class ApiServiceImpl : ApiService {

    override suspend fun fetchProductPrices(productId: ProductId): List<ProductPrice> {
        return listOf(
            ProductPrice(
                id = 1,
                productId = productId,
                shopName = "Sigma",
                url = "https://sigma.by",
                imageUrl = "file:///android_asset/sigma.png",
                price = 2665.72,
                currency = "BYN",
            ),
        )
    }

    override suspend fun fetchProductImages(productId: ProductId): List<ProductImage> {
        return listOf(
            ProductImage(
                id = 1,
                productId = productId,
                imageUrl = "file:///android_asset/sapphire.png",
            ),
            ProductImage(
                id = 2,
                productId = productId + 1,
                imageUrl = "file:///android_asset/palit.png",
            ),
            ProductImage(
                id = 3,
                productId = productId + 2,
                imageUrl = "file:///android_asset/msi.png",
            ),
            ProductImage(
                id = 4,
                productId = productId + 3,
                imageUrl = "file:///android_asset/gigabyte.png",
            ),
        )
    }

    override suspend fun fetchProductSpecs(productId: ProductId): List<ProductSpec> {
        return listOf(
            ProductSpec(
                id = 1,
                productId = productId,
                type = "Подбор в один клик",
                value = "с трассировкой лучей, для игр в 4K",
            ),
            ProductSpec(
                id = 2,
                productId = productId,
                type = "Интерфейс",
                value = "PCI Express x16 4.0",
            ),
            ProductSpec(
                id = 3,
                productId = productId,
                type = "Производитель графического процессора",
                value = "AMD",
            ),
            ProductSpec(
                id = 4,
                productId = productId,
                type = "Микроархитектура",
                value = "AMD RDNA 3.0",
            ),
            ProductSpec(
                id = 5,
                productId = productId,
                type = "Кодовое имя чипа",
                value = "Navi 31",
            ),
            ProductSpec(
                id = 6,
                productId = productId,
                type = "«Разогнанная» версия",
                value = "true",
            ),
            ProductSpec(
                id = 7,
                productId = productId,
                type = "Внешняя видеокарта",
                value = "false",
            ),
        )
    }

    override suspend fun fetchProductImage(productId: ProductId): ProductImage {
        return when (productId) {
            192385 -> ProductImage(
                id = 2,
                productId = productId,
                imageUrl = "file:///android_asset/gigabyte.png",
            )

            192386 -> ProductImage(
                id = 3,
                productId = productId,
                imageUrl = "file:///android_asset/palit.png",
            )

            192387 -> ProductImage(
                id = 4,
                productId = productId,
                imageUrl = "file:///android_asset/msi.png",
            )

            else -> ProductImage(
                id = 1,
                productId = productId,
                imageUrl = "file:///android_asset/sapphire.png",
            )
        }
    }

    override suspend fun fetchCityNames(): List<String> {
        return listOf(
            "Гродно", "Минск", "Могилев", "Витебск", "Брест", "Гомель"
        )
    }

    override suspend fun fetchProduct(productId: ProductId): Product {
        return Product(
            id = productId,
            type = "GPU",
            apiId = "192384",
            name = "RX 7900",
            fullName = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
            description = "Видеокарта Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G - это мощное графическое устройство с поддержкой трассировки лучей, идеально подходящее для игр в 4K.\nС поддержкой DirectX 12 Ultimate, эта видеокарта обеспечивает высокую производительность и качество графики.\nС разогнанной версией графического процессора и подсветкой, она предлагает улучшенный игровой опыт.\nС 16 ГБ видеопамяти и 80 RT-ядрами, эта видеокарта обеспечивает высокую производительность и плавную работу в самых требовательных играх.",
            rating = 3.4f,
        )
    }

    override suspend fun fetchProducts(productType: String): List<Product> {
        return listOf(
            Product(
                id = 192384,
                type = productType,
                apiId = "192384",
                name = "RX 7900",
                fullName = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                description = "Видеокарта Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G - это мощное графическое устройство с поддержкой трассировки лучей, идеально подходящее для игр в 4K.\nС поддержкой DirectX 12 Ultimate, эта видеокарта обеспечивает высокую производительность и качество графики.\nС разогнанной версией графического процессора и подсветкой, она предлагает улучшенный игровой опыт.\nС 16 ГБ видеопамяти и 80 RT-ядрами, эта видеокарта обеспечивает высокую производительность и плавную работу в самых требовательных играх.",
                rating = 3.4f,
            ),
            Product(
                id = 192385,
                type = productType,
                apiId = "192384",
                name = "RTX 4060",
                fullName = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GD",
                description = "Видеокарта Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G - это мощное графическое устройство с поддержкой трассировки лучей, идеально подходящее для игр в 4K.\nС поддержкой DirectX 12 Ultimate, эта видеокарта обеспечивает высокую производительность и качество графики.\nС разогнанной версией графического процессора и подсветкой, она предлагает улучшенный игровой опыт.\nС 16 ГБ видеопамяти и 80 RT-ядрами, эта видеокарта обеспечивает высокую производительность и плавную работу в самых требовательных играх.",
                rating = 3.4f,
            ),
            Product(
                id = 192386,
                type = productType,
                apiId = "192384",
                name = "RTX 4060",
                fullName = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                description = "Видеокарта Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G - это мощное графическое устройство с поддержкой трассировки лучей, идеально подходящее для игр в 4K.\nС поддержкой DirectX 12 Ultimate, эта видеокарта обеспечивает высокую производительность и качество графики.\nС разогнанной версией графического процессора и подсветкой, она предлагает улучшенный игровой опыт.\nС 16 ГБ видеопамяти и 80 RT-ядрами, эта видеокарта обеспечивает высокую производительность и плавную работу в самых требовательных играх.",
                rating = 3.4f,
            ),
            Product(
                id = 192387,
                type = productType,
                apiId = "192384",
                name = "RTX 4060 Ti",
                fullName = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                description = "Видеокарта Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G - это мощное графическое устройство с поддержкой трассировки лучей, идеально подходящее для игр в 4K.\nС поддержкой DirectX 12 Ultimate, эта видеокарта обеспечивает высокую производительность и качество графики.\nС разогнанной версией графического процессора и подсветкой, она предлагает улучшенный игровой опыт.\nС 16 ГБ видеопамяти и 80 RT-ядрами, эта видеокарта обеспечивает высокую производительность и плавную работу в самых требовательных играх.",
                rating = 3.4f,
            ),
        )
    }

    override suspend fun fetchFilters(productType: ProductType): List<Filter> {
        return listOf(
            Filter(
                id = 1,
                filterName = "Производитель",
                filterType = FilterType.SELECT,
                variants = listOf("Palit", "Gigabyte", "MSI", "Asus", "Sapphire", "Zotac", "EVGA", "GALAX"),
            ),
            Filter(
                id = 1,
                filterName = "Цена",
                filterType = FilterType.RANGE,
                variants = listOf("1200", "2500"),
            ),
            Filter(
                id = 2,
                filterName = "Подсветка",
                filterType = FilterType.RADIO,
                variants = listOf("да", "нет"),
            ),
            Filter(
                id = 3,
                filterName = "Производитель",
                filterType = FilterType.SELECT,
                variants = listOf("Palit", "Gigabyte", "MSI", "Asus", "Sapphire", "Zotac", "EVGA", "GALAX"),
            ),
            Filter(
                id = 4,
                filterName = "Производитель",
                filterType = FilterType.SELECT,
                variants = listOf("Palit", "Gigabyte", "MSI", "Asus", "Sapphire", "Zotac", "EVGA", "GALAX"),
            ),
        )
    }
}