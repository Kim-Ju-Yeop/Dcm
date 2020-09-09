package kr.hs.dgsw.juyeop.dcm_android.view.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_add.*
import kr.hs.dgsw.juyeop.dcm_android.base.view.BaseActivity
import kr.hs.dgsw.juyeop.dcm_android.databinding.ActivityAddBinding
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity.AddViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.`object`.ImageManager
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.longToastMessage
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.shortToastMessage
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.startActivityNoFinish
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AddActivity : BaseActivity<ActivityAddBinding, AddViewModel>() {

    override val viewModel: AddViewModel
        get() = getViewModel(AddViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onBackEvent.observe(this@AddActivity, Observer {
                onBackPressed()
            })
            onCameraEvent.observe(this@AddActivity, Observer {
                startActivityNoFinish(applicationContext, CameraKitActivity::class.java)
            })
            onFailureEvent.observe(this@AddActivity, Observer {
                shortToastMessage("빈 칸 없이 각 항목들을 입력해주시기 바랍니다.")
            })
            onFailureData.observe(this@AddActivity, Observer {
                longToastMessage(it.message.toString())
            })
            onSuccessEvent.observe(this@AddActivity, Observer {
                startActivityWithFinish(applicationContext, MainActivity::class.java)
            })
        }
    }

    override fun onResume() {
        super.onResume()

        if (ImageManager.byteArray.isNotEmpty()) {
            viewModel.imageByteArray = ImageManager.byteArray

            var bitmap = BitmapFactory.decodeByteArray(viewModel.imageByteArray, 0, viewModel.imageByteArray.size)
            bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false)
            imageView.setImageBitmap(bitmap)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ImageManager.byteArray = ByteArray(0)
    }
}