package kr.hs.dgsw.juyeop.dcm_android.view.activity

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add.imageView
import kotlinx.android.synthetic.main.activity_modify.*
import kr.hs.dgsw.juyeop.dcm_android.base.view.BaseActivity
import kr.hs.dgsw.juyeop.dcm_android.databinding.ActivityModifyBinding
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity.ModifyViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.longToastMessage
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.shortToastMessage
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.startActivityWithFinish
import kr.hs.dgsw.juyeop.domain.ProductModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ModifyActivity : BaseActivity<ActivityModifyBinding, ModifyViewModel>() {

    override val viewModel: ModifyViewModel
        get() = getViewModel(ModifyViewModel::class)

    override fun init() {
        viewModel.productModel = intent.getSerializableExtra("productModel") as ProductModel
        Glide.with(applicationContext).load(viewModel.productModel.imageUrl).into(imageView)

        viewModel.setData()

        if (viewModel.productModel.rentAble == 1) {
            rentAbleSpinner.visibility = View.GONE
            rentUser.visibility = View.GONE
        } else {
            setSpinner()
        }
    }

    override fun observerViewModel() {
        with(viewModel) {
            onBackEvent.observe(this@ModifyActivity, Observer {
                onBackPressed()
            })
            onSuccessEvent.observe(this@ModifyActivity, Observer {
                startActivityWithFinish(applicationContext, MainActivity::class.java)
            })
            onFailEvent.observe(this@ModifyActivity, Observer {
                shortToastMessage("빈 칸 없이 각 항목들을 입력해주시기 바랍니다.")
            })
            onFailureData.observe(this@ModifyActivity, Observer {
                longToastMessage(it.message.toString())
            })
        }
    }

    private fun setSpinner() {
        val arrayList = ArrayList<String>()
        arrayList.add("대여 중")
        arrayList.add("대여 가능")

        val arrayAdapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_dropdown_item_1line, arrayList)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                if (arrayList[i] == "대여 중") {
                    rentUser.visibility = View.VISIBLE
                    viewModel.rentAble.value = 0
                    viewModel.findRentUser()
                } else {
                    viewModel.rentAble.value = 1
                    viewModel.rentUser.value = ""
                    rentUser.visibility = View.GONE
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}