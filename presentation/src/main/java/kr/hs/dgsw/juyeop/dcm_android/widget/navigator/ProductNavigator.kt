package kr.hs.dgsw.juyeop.dcm_android.widget.navigator

import kr.hs.dgsw.juyeop.domain.ProductModel

interface ProductNavigator {
    fun onClickItem(model: ProductModel)
}