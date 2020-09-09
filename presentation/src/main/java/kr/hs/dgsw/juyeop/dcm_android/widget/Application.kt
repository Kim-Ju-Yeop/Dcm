package kr.hs.dgsw.juyeop.dcm_android.widget

import android.app.Application
import kr.hs.dgsw.juyeop.dcm_android.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            val modules = listOf(viewModelModule)
            modules(modules)
        }
    }
}