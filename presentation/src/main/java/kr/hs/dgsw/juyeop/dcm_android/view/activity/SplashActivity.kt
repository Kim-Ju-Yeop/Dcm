package kr.hs.dgsw.juyeop.dcm_android.view.activity

import android.os.Handler
import androidx.lifecycle.Observer
import kr.hs.dgsw.juyeop.dcm_android.base.view.BaseActivity
import kr.hs.dgsw.juyeop.dcm_android.databinding.ActivitySplashBinding
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity.SplashViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.startActivityWithFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel
        get() = getViewModel(SplashViewModel::class)

    override fun init() {}
    override fun observerViewModel() {
        val handler = Handler()
        var runnable : Runnable

        with(viewModel) {
            onFailEvent.observe(this@SplashActivity, Observer {
                runnable = Runnable { startActivityWithFinish(applicationContext, SignInActivity::class.java) }
                handler.postDelayed(runnable, 2000)
            })
            onSuccessEvent.observe(this@SplashActivity, Observer {
                runnable = Runnable { startActivityWithFinish(applicationContext, MainActivity::class.java) }
                handler.postDelayed(runnable, 2000)
            })
        }
    }
}