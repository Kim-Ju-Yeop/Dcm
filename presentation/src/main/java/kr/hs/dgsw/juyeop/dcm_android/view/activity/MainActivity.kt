package kr.hs.dgsw.juyeop.dcm_android.view.activity

import kotlinx.android.synthetic.main.activity_main.*
import kr.hs.dgsw.juyeop.dcm_android.R
import kr.hs.dgsw.juyeop.dcm_android.base.view.BaseActivity
import kr.hs.dgsw.juyeop.dcm_android.databinding.ActivityMainBinding
import kr.hs.dgsw.juyeop.dcm_android.view.fragment.AccountFragment
import kr.hs.dgsw.juyeop.dcm_android.view.fragment.CurrentFragment
import kr.hs.dgsw.juyeop.dcm_android.view.fragment.HomeFragment
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val homeFragment = HomeFragment()
    private val currentFragment = CurrentFragment()
    private val accountFragment = AccountFragment()

    override val viewModel: MainViewModel
        get() = getViewModel(MainViewModel::class)

    override fun init() {
        val fragmentTranscation = supportFragmentManager.beginTransaction()
        fragmentTranscation.replace(R.id.page_fragment, homeFragment).commitAllowingStateLoss()
        bottomNavigationEvent()
    }

    override fun observerViewModel() {}
    private fun bottomNavigationEvent() {
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            val fragmentTranscation = supportFragmentManager.beginTransaction()
            when(item.itemId) {
                R.id.home -> fragmentTranscation.replace(R.id.page_fragment, homeFragment).commitAllowingStateLoss()
                R.id.current -> fragmentTranscation.replace(R.id.page_fragment, currentFragment).commitAllowingStateLoss()
                R.id.account -> fragmentTranscation.replace(R.id.page_fragment, accountFragment).commitAllowingStateLoss()
            }
            true
        }
    }
}