package kr.hs.dgsw.juyeop.dcm_android.view.activity

import androidx.lifecycle.Observer
import com.camerakit.CameraKitView
import kotlinx.android.synthetic.main.activity_camera_kit.*
import kr.hs.dgsw.juyeop.dcm_android.base.view.BaseActivity
import kr.hs.dgsw.juyeop.dcm_android.databinding.ActivityCameraKitBinding
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity.CameraKitViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.`object`.ImageManager
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CameraKitActivity : BaseActivity<ActivityCameraKitBinding, CameraKitViewModel>() {

    override val viewModel: CameraKitViewModel
        get() = getViewModel(CameraKitViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        with(viewModel) {
            onDetectEvent.observe(this@CameraKitActivity, Observer {
                cameraKitView.captureImage(object : CameraKitView.ImageCallback {
                    override fun onImage(p0: CameraKitView?, p1: ByteArray?) {
                        ImageManager.byteArray = p1!!
                        onBackPressed()
                    }
                })
            })
        }
    }

    override fun onStart() {
        super.onStart()
        cameraKitView.onStart()
    }
    override fun onResume() {
        super.onResume()
        cameraKitView.onResume()
    }
    override fun onPause() {
        super.onPause()
        cameraKitView.onPause()
    }
    override fun onStop() {
        super.onStop()
        cameraKitView.onStop()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}