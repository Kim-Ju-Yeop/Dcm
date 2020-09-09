package kr.hs.dgsw.juyeop.dcm_android.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kr.hs.dgsw.juyeop.dcm_android.BR
import kr.hs.dgsw.juyeop.dcm_android.R
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*

abstract class BaseActivity <VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    private lateinit var binding : VB
    protected abstract val viewModel : VM

    protected abstract fun init()
    protected abstract fun observerViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        observerViewModel()
        init()
    }

    private fun performDataBinding(){
        binding = DataBindingUtil.setContentView(this, layoutRes())
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    @LayoutRes
    private fun layoutRes() : Int{
        val split = ((Objects.requireNonNull<Type>(javaClass.genericSuperclass) as ParameterizedType).actualTypeArguments[0] as Class<*>)
            .simpleName.replace("Binding$".toRegex(), "")
            .split("(?<=.)(?=\\p{Upper})".toRegex())
            .dropLastWhile { it.isEmpty() }.toTypedArray()

        val name = StringBuilder()
        for (i in split.indices) {
            name.append(split[i].toLowerCase(Locale.ROOT))
            if (i != split.size - 1) name.append("_")
        }
        return R.layout::class.java.getField(name.toString()).getInt(R.layout::class.java)
    }

    override fun onBackPressed() {
        this.finish()
    }
}