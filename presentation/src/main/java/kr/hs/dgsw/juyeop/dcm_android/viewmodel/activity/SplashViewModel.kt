package kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity

import android.content.Context
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.SingleLiveEvent
import kr.hs.dgsw.juyeop.dcm_android.widget.`object`.SharedPreferencesManager

class SplashViewModel(
    context: Context
) : BaseViewModel() {

    val onFailEvent = SingleLiveEvent<Unit>()
    val onSuccessEvent = SingleLiveEvent<Unit>()

    init {
        if (SharedPreferencesManager.getUserUid(context) != null) onSuccessEvent.call()
        else onFailEvent.call()
    }
}