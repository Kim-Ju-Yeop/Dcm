package kr.hs.dgsw.juyeop.dcm_android.view.activity

import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kr.hs.dgsw.juyeop.dcm_android.base.view.BaseActivity
import kr.hs.dgsw.juyeop.dcm_android.databinding.ActivityDetailBinding
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity.DetailViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.longToastMessage
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.startActivityWithExtraNoFinish
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.startActivityWithFinish
import kr.hs.dgsw.juyeop.domain.ProductModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override val viewModel: DetailViewModel
        get() = getViewModel(DetailViewModel::class)

    override fun init() {
        viewModel.productModel = intent.getSerializableExtra("productModel") as ProductModel
        Glide.with(applicationContext).load(viewModel.productModel.imageUrl).into(image)

        when (viewModel.productModel.rentAble) {
            0 -> {
                linearLayout2.visibility = View.GONE
                viewModel.findRentUser()
                rentAble.setTextColor(Color.parseColor("#CE0000"))
            }
            1 -> {
                linearLayout2.visibility = View.GONE
                rentUser.visibility = View.GONE
                rentAble.setTextColor(Color.parseColor("#1B8900"))
            }
            2 -> {
                linearLayout3.visibility = View.GONE
                viewModel.findRentUser()
                rentAble.setTextColor(Color.parseColor("#E4A400"))
            }
        }

        viewModel.setUpData()
    }

    override fun observerViewModel() {
        with(viewModel) {
            onBackEvent.observe(this@DetailActivity, Observer {
                onBackPressed()
            })
            onSuccessEvent.observe(this@DetailActivity, Observer {
                startActivityWithFinish(applicationContext, MainActivity::class.java)
            })
            onFailureData.observe(this@DetailActivity, Observer {
                longToastMessage(it.message.toString())
            })
            onModifyEvent.observe(this@DetailActivity, Observer {
                startActivityWithExtraNoFinish(Intent(applicationContext, ModifyActivity::class.java).putExtra("productModel", productModel))
            })
        }
    }
}