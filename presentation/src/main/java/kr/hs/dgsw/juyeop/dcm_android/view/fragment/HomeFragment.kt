package kr.hs.dgsw.juyeop.dcm_android.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kr.hs.dgsw.juyeop.dcm_android.base.view.BaseFragment
import kr.hs.dgsw.juyeop.dcm_android.databinding.FragmentHomeBinding
import kr.hs.dgsw.juyeop.dcm_android.view.activity.AddActivity
import kr.hs.dgsw.juyeop.dcm_android.view.activity.DetailActivity
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.fragment.HomeViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.startActivityNoFinish
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.startActivityWithExtraNoFinish
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val mViewModel: HomeViewModel
        get() = getViewModel(HomeViewModel::class)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabSelectedEvent()
    }

    override fun observerVieModel() {
        with(mViewModel) {
            productAdapter.clickProduct.observe(this@HomeFragment, Observer {
                startActivityWithExtraNoFinish(Intent(context!!.applicationContext, DetailActivity::class.java).putExtra("productModel", it))
            })
            onAddEvent.observe(this@HomeFragment, Observer {
                startActivityNoFinish(context!!.applicationContext, AddActivity::class.java)
            })
        }
    }

    private fun tabSelectedEvent() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> mViewModel.entireList()
                    1 -> mViewModel.rentableList()
                    2 -> mViewModel.lendingList()
                }
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }
}