package kr.hs.dgsw.juyeop.dcm_android.widget.recyclerview.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.hs.dgsw.juyeop.dcm_android.R
import kr.hs.dgsw.juyeop.dcm_android.databinding.ItemProductBinding
import kr.hs.dgsw.juyeop.dcm_android.widget.navigator.ProductNavigator
import kr.hs.dgsw.juyeop.dcm_android.widget.recyclerview.viewmodel.ProductViewModel
import kr.hs.dgsw.juyeop.domain.ProductModel

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>(), ProductNavigator {

    var clickProduct = MutableLiveData<ProductModel>()
    var productList = ArrayList<ProductModel>()

    fun setList(productList: ArrayList<ProductModel>) {
        this.productList = productList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onClickItem(model: ProductModel) {
        clickProduct.value = model
    }

    inner class ViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ProductViewModel()

        fun bind(model: ProductModel) {
            settingData(model)

            viewModel.bind(model)
            viewModel.setNavigator(this@ProductAdapter)
            binding.viewModel = viewModel
        }

        private fun settingData(model: ProductModel) {
            Glide.with(binding.root).load(model.imageUrl).into(binding.image)

            when (model.rentAble) {
                0 -> binding.rentAble.setTextColor(Color.parseColor("#CE0000"))
                1 -> binding.rentAble.setTextColor(Color.parseColor("#1B8900"))
                2 -> binding.rentAble.setTextColor(Color.parseColor("#E4A400"))
            }
        }
    }
}