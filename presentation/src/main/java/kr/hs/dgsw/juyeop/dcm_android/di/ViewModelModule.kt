package kr.hs.dgsw.juyeop.dcm_android.di

import kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity.*
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.fragment.AccountViewmodel
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.fragment.CurrentViewModel
import kr.hs.dgsw.juyeop.dcm_android.viewmodel.fragment.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { DetailViewModel(get()) }
    viewModel { AddViewModel() }
    viewModel { CameraKitViewModel() }
    viewModel { ModifyViewModel() }
    viewModel { SubmitViewModel() }

    viewModel { HomeViewModel() }
    viewModel { CurrentViewModel() }
    viewModel { AccountViewmodel(get()) }
}