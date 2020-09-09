package kr.hs.dgsw.juyeop.dcm_android.widget.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.hs.dgsw.juyeop.dcm_android.R
import kr.hs.dgsw.juyeop.dcm_android.databinding.ItemSubmitBinding
import kr.hs.dgsw.juyeop.dcm_android.widget.navigator.SubmitNavigator
import kr.hs.dgsw.juyeop.dcm_android.widget.recyclerview.viewmodel.SubmitViewModel
import kr.hs.dgsw.juyeop.domain.SubmitModel

class SubmitAdapter : RecyclerView.Adapter<SubmitAdapter.ViewHolder>(), SubmitNavigator {

    var clickSubmit = MutableLiveData<SubmitModel>()
    var submitList = ArrayList<SubmitModel>()

    fun setList(submitList: ArrayList<SubmitModel>) {
        this.submitList = submitList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_submit, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(submitList[position])
    }

    override fun getItemCount(): Int {
        return submitList.size
    }

    override fun onClickItem(model: SubmitModel) {
        clickSubmit.value = model
    }

    inner class ViewHolder(private val binding: ItemSubmitBinding): RecyclerView.ViewHolder(binding.root) {
        private val viewModel = SubmitViewModel()

        fun bind(model: SubmitModel) {
            Glide.with(binding.root).load(model.imageUrl).into(binding.image)

            viewModel.bind(model)
            viewModel.setNavigator(this@SubmitAdapter)
            binding.viewModel = viewModel
        }
    }
}