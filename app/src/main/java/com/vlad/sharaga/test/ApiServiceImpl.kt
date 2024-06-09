package com.vlad.sharaga.test

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vlad.sharaga.core.adapter.recycler.fingerprints.BudgetAssemblyItem
import com.vlad.sharaga.core.adapter.recycler.fingerprints.ProductPreviewItem
import com.vlad.sharaga.core.adapter.spinner.SearchResultItem
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.models.Filter
import com.vlad.sharaga.models.FilterDTO
import com.vlad.sharaga.models.FilterType
import com.vlad.sharaga.models.Product
import com.vlad.sharaga.models.ProductImage
import com.vlad.sharaga.models.ProductPrice
import com.vlad.sharaga.models.ProductSpec
import com.vlad.sharaga.models.ProductType
import com.vlad.sharaga.network.api.ApiService
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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

    override suspend fun fetchProduct(productId: ProductId): Product? {
        val url = URL("https://vlad.joomboosick.ru/api/products/$productId")

        val openedConnection = url.openConnection() as HttpURLConnection
        openedConnection.requestMethod = "GET"

        val responseCode = openedConnection.responseCode
        try {
            val reader = BufferedReader(InputStreamReader(openedConnection.inputStream))
            val response = reader.readText()
            reader.close()

            val product = Gson().fromJson(response, Product::class.java)
            return  product
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
        } finally {

        }
        return null
    }

    override suspend fun fetchProducts(productType: String): List<Product> {
        var products = listOf<Product>()
        val url = URL("https://vlad.joomboosick.ru/api/products/$productType?priceMin=0&priceMax=1000&page=1&per_page=1000")

        val openedConnection = url.openConnection() as HttpURLConnection
        openedConnection.requestMethod = "GET"

        val responseCode = openedConnection.responseCode
        try {
            val reader = BufferedReader(InputStreamReader(openedConnection.inputStream))
            val response = reader.readText()
            reader.close()
            val productListType = object : TypeToken<List<Product>>() {}.type

            products = Gson().fromJson(response, productListType)
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
        } finally {

        }
        products.forEach{ product ->
            product.rating /= 10
        }
        return products
    }

    override suspend fun fetchFilters(productType: ProductType): List<Filter> {
        /*
        * Filter(
                id = 1,
                filterName = "Производитель",
                filterType = FilterType.SELECT,
                variants = listOf("Palit", "Gigabyte", "MSI", "Asus", "Sapphire", "Zotac", "EVGA", "GALAX"),
            )
        *
        * */
        val filters: HashMap<Int, Filter> = hashMapOf<Int, Filter>()

        var filterDtos = listOf<FilterDTO>()
        val url = URL("https://vlad.joomboosick.ru/api/product/filter/$productType")

        val openedConnection = url.openConnection() as HttpURLConnection
        openedConnection.requestMethod = "GET"

        val responseCode = openedConnection.responseCode
        try {
            val reader = BufferedReader(InputStreamReader(openedConnection.inputStream))
            val response = reader.readText()
            reader.close()
            val productListType = object : TypeToken<List<FilterDTO>>() {}.type

            filterDtos = Gson().fromJson(response, productListType)
        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
        } finally {

        }
        filterDtos.forEach { dto ->
            if(filters.containsKey(dto.filterType)){
                filters[dto.filterType]?.variants?.add(dto.value)
            }
            else{
                filters[dto.filterType] = Filter(dto.id, dto.filterFriendlyName, FilterType.SELECT, mutableListOf<String>(dto.value) )
            }
        }

        filters[-1] = Filter(
            id = 1,
            filterName = "Цена",
            filterType = FilterType.RANGE,
            variants = mutableListOf("150", "3000"),
        )

        return filters.values.toList()
    }

    override suspend fun fetchAssemblies(budgetPrice: Int): List<BudgetAssemblyItem> {
        return listOf(
            BudgetAssemblyItem(
                id = 1,
                price = 2500.0,
                products = listOf(
                    ProductPreviewItem(
                        productId = 192384,
                        imageUrl = "file:///android_asset/sapphire.png",
                        title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                    ProductPreviewItem(
                        productId = 192385,
                        imageUrl = "file:///android_asset/gigabyte.png",
                        title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GD",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                    ProductPreviewItem(
                        productId = 192386,
                        imageUrl = "file:///android_asset/palit.png",
                        title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                    ProductPreviewItem(
                        productId = 192387,
                        imageUrl = "file:///android_asset/msi.png",
                        title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                )
            ),
            BudgetAssemblyItem(
                id = 2,
                price = 2700.0,
                products = listOf(
                    ProductPreviewItem(
                        productId = 192384,
                        imageUrl = "file:///android_asset/sapphire.png",
                        title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                    ProductPreviewItem(
                        productId = 192385,
                        imageUrl = "file:///android_asset/gigabyte.png",
                        title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GD",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                    ProductPreviewItem(
                        productId = 192386,
                        imageUrl = "file:///android_asset/palit.png",
                        title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                    ProductPreviewItem(
                        productId = 192387,
                        imageUrl = "file:///android_asset/msi.png",
                        title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                )
            ),
            BudgetAssemblyItem(
                id = 3,
                price = 3000.0,
                products = listOf(
                    ProductPreviewItem(
                        productId = 192384,
                        imageUrl = "file:///android_asset/sapphire.png",
                        title = "Sapphire Nitro+ Radeon RX 7900 GRE 16GB 11325-02-20G",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                    ProductPreviewItem(
                        productId = 192385,
                        imageUrl = "file:///android_asset/gigabyte.png",
                        title = "Gigabyte GeForce RTX 4060 Gaming OC 8G GV-N4060GAMING OC-8GD",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                    ProductPreviewItem(
                        productId = 192386,
                        imageUrl = "file:///android_asset/palit.png",
                        title = "Palit GeForce RTX 4060 Dual OC NE64060T19P1-1070D",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                    ProductPreviewItem(
                        productId = 192387,
                        imageUrl = "file:///android_asset/msi.png",
                        title = "MSI GeForce RTX 4060 Ti Gaming X 8G",
                        rating = 3.4f,
                        minPrice = 2665.72,
                        variants = 3,
                    ),
                )
            ),
        )
    }

    override suspend fun searchProducts(query: String): List<SearchResultItem> {
        return listOf(
            SearchResultItem(
                productId = 192384,
                title = "RX 7900",
                price = "2665.72",
                imageUrl = "file:///android_asset/sapphire.png",
            ),
            SearchResultItem(
                productId = 192385,
                title = "RTX 4060",
                price = "2665.72",
                imageUrl = "file:///android_asset/gigabyte.png",
            ),
            SearchResultItem(
                productId = 192386,
                title = "RTX 4060",
                price = "2665.72",
                imageUrl = "file:///android_asset/palit.png",
            ),
            SearchResultItem(
                productId = 192387,
                title = "RTX 4060 Ti",
                price = "2665.72",
                imageUrl = "file:///android_asset/msi.png",
            ),
        )
    }
}