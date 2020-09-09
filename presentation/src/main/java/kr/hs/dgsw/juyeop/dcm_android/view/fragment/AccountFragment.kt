package kr.hs.dgsw.juyeop.dcm_android.view.fragment

import androidx.lifecycle.Observer
import kr.hs.dgsw.juyeop.dcm_android.base.view.BaseFragment
import kr.hs.dgsw.juyeop.dcm_android.databinding.FragmentAccountBinding
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.fragment.AccountViewmodel
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.longToastMessage
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewmodel>() {

    override val mViewModel: AccountViewmodel
        get() = getViewModel(AccountViewmodel::class)

    override fun observerVieModel() {
        with(mViewModel) {
            onFailureData.observe(this@AccountFragment, Observer {
                longToastMessage(it.message.toString())
            })
        }
    }
}