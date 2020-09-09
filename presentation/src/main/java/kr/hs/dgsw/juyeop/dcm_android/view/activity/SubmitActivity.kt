package kr.hs.dgsw.juyeop.dcm_android.view.activity

import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_submit.*
import kr.hs.dgsw.juyeop.dcm_android.base.view.BaseActivity
import kr.hs.dgsw.juyeop.dcm_android.databinding.ActivitySubmitBinding
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity.SubmitViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.longToastMessage
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.startActivityWithFinish
import kr.hs.dgsw.juyeop.domain.SubmitModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SubmitActivity : BaseActivity<ActivitySubmitBinding, SubmitViewModel>() {

    override val viewModel: SubmitViewModel
        get() = getViewModel(SubmitViewModel::class)

    override fun init() {
        viewModel.submitModel = intent.getSerializableExtra("submitModel") as SubmitModel
        Glide.with(applicationContext).load(viewModel.submitModel.imageUrl).into(imageView)

        viewModel.setData()
    }

    override fun observerViewModel() {
        with(viewModel) {
            onFailureData.observe(this@SubmitActivity, Observer {
                longToastMessage(it.message.toString())
            })
            onSuccessEvent.observe(this@SubmitActivity, Observer {
                startActivityWithFinish(applicationContext, MainActivity::class.java)
            })
        }
    }

}