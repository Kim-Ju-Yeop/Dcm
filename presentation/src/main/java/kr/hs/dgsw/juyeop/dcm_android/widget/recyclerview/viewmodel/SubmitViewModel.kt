package kr.hs.dgsw.juyeop.dcm_android.widget.recyclerview.viewmodel

import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseItemViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.navigator.SubmitNavigator
import kr.hs.dgsw.juyeop.domain.ProductModel
import kr.hs.dgsw.juyeop.domain.SubmitModel

class SubmitViewModel : BaseItemViewModel<SubmitNavigator>() {

    private lateinit var model: SubmitModel

    val name = MutableLiveData<String>()
    val submitAble = MutableLiveData<String>()

    fun bind(model: SubmitModel) {
        this.model = model

        name.value = model.name
        when (model.submitAble) {
            0 -> submitAble.value = "승인 대기 중"
            1 -> submitAble.value = "승인됨"
            2 -> submitAble.value = "거절됨"
        }
    }

    fun onClickItem() {
        getNavigator().onClickItem(model)
    }
}