package kr.hs.dgsw.juyeop.dcm_android.widget.recyclerview.viewmodel

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseItemViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.navigator.ProductNavigator
import kr.hs.dgsw.juyeop.domain.ProductModel

class ProductViewModel : BaseItemViewModel<ProductNavigator>() {

    private lateinit var model: ProductModel

    val name = MutableLiveData<String>()
    val rentAble = MutableLiveData<String>()

    fun bind(model: ProductModel) {
        this.model = model

        name.value = model.name
        when (model.rentAble) {
            0 -> rentAble.value = "대여 중"
            1 -> rentAble.value = "대여 가능"
            2 -> rentAble.value = "승인 대기 중"
        }
    }

    fun onClickItem() {
        getNavigator().onClickItem(model)
    }
}