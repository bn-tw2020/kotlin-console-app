package screen

import LINE_DIVIDER
import data.CartItems
import data.Product
import extensions.getNotEmptyInt
import extensions.getNotEmptyString

/*
    1. 장바구니에 추가한 상품 관리
    2. 사용자 입력값 요청 처리 공통화
    3. 프로젝트 전역에서 참조하는 상수
 */
class ShoppingProductList {
    private val products = arrayOf(
        Product("패션", "겨울 패딩"),
        Product("패션", "겨울 바지"),
        Product("전자기기", "핸드폰"),
        Product("전자기기", "블루투스 이어폰"),
        Product("반련동물용품", "노트북"),
        Product("반련동물용품", "건식사료"),
        Product("반련동물용품", "습식사료"),
        Product("반련동물용품", "치약"),
        Product("반련동물용품", "간식"),
    )
    private val categories: Map<String, List<Product>> = products.groupBy { product ->
        product.categoryLabel
    }

    fun showProducts(selectedCategory: String) {
        val categoryProducts = categories[selectedCategory]
        if (!categoryProducts.isNullOrEmpty()) {
            println(
                """
                $LINE_DIVIDER
                선택하신 [${selectedCategory}] 카테고리 상품입니다.
                """.trimIndent()
            )

            categoryProducts.forEachIndexed { index, product ->
                println("${index}. ${product.name}")
            }

            showCartOption(categoryProducts, selectedCategory)
        } else {
            showEmptyProductMessage(selectedCategory)
        }
    }

    private fun showCartOption(categoryProducts: List<Product>, selectedCategory: String) {
        println(
            """
            $LINE_DIVIDER
            장바구니에 담을 상품 번호를 선택해주세요.
            """.trimIndent()
        )

        val selectedIndex = readLine().getNotEmptyInt()
        categoryProducts.getOrNull(selectedIndex)?.let { product ->
            CartItems.addProduct(product)
            println("=> 장바구니로 이동하시려면 #을, 계속 쇼핑하시려면 *을 입력해주세요.")
            val answer = readLine().getNotEmptyString()
            if (answer == "#") {
                val shoppingCart = ShoppingCart()
                shoppingCart.showCartItems()
            }
            else if(answer == "*") {
                showProducts(selectedCategory)
            }
            else {
                // TODO(그 외 값으 ㄹ입력한 경우)
            }
        }
    }


    private fun showEmptyProductMessage(selectedCategory: String) {
        println("[$selectedCategory] 카테고리의 상품이 등록되기 전입니다.")
    }
}