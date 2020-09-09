package kr.hs.dgsw.juyeop.dcm_android.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_current.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.tabLayout
import kr.hs.dgsw.juyeop.dcm_android.base.view.BaseFragment
import kr.hs.dgsw.juyeop.dcm_android.databinding.FragmentCurrentBinding
import kr.hs.dgsw.juyeop.dcm_android.view.activity.DetailActivity
import kr.hs.dgsw.juyeop.dcm_android.view.activity.SubmitActivity
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.fragment.CurrentViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.startActivityWithExtraNoFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CurrentFragment : BaseFragment<FragmentCurrentBinding, CurrentViewModel>() {

    override val mViewModel: CurrentViewModel
        get() = getViewModel(CurrentViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.lendingProductList()
        tabSelectedEvent()
    }

    override fun observerVieModel() {
        with(mViewModel) {
            productAdapter.clickProduct.observe(this@CurrentFragment, Observer {
                startActivityWithExtraNoFinish(Intent(context!!.applicationContext, DetailActivity::class.java).putExtra("productModel", it))
            })
            submitAdapter.clickSubmit.observe(this@CurrentFragment, Observer {
                startActivityWithExtraNoFinish(Intent(context!!.applicationContext, SubmitActivity::class.java).putExtra("submitModel", it))
            })
        }
    }

    private fun tabSelectedEvent() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        productRecyclerView.visibility = View.VISIBLE
                        submitRecyclerView.visibility = View.GONE

                        mViewModel.lendingProductList()
                    }
                    1 -> {
                        productRecyclerView.visibility = View.GONE
                        submitRecyclerView.visibility = View.VISIBLE

                        mViewModel.lendingSubmitList()
                    }
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }
}