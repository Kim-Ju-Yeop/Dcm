package kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.SingleLiveEvent
import kr.hs.dgsw.juyeop.domain.ProductModel
import java.lang.Exception

class ModifyViewModel : BaseViewModel() {

    lateinit var productModel: ProductModel

    val name = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    var rentAble = MutableLiveData<Int>()
    val rentUser = MutableLiveData<String>()
    val onFailureData = MutableLiveData<Exception>()

    val onBackEvent = SingleLiveEvent<Unit>()
    val onSuccessEvent = SingleLiveEvent<Unit>()
    val onFailEvent = SingleLiveEvent<Unit>()

    val firebaseFirestore = FirebaseFirestore.getInstance()

    fun setData() {
        name.value = productModel.name
        rentAble.value = productModel.rentAble
        rentUser.value = productModel.rentUser
        content.value = productModel.content
    }

    fun findRentUser() {
        firebaseFirestore.collection("student").document(productModel.rentUser)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    rentUser.value = task.result?.get("name").toString()
                }
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }

    fun backEvent() {
        onBackEvent.call()
    }

    fun modify() {
        if (checkData()) {
            val updateData: MutableMap<String, Any> = HashMap()
            updateData["name"] =  name.value.toString()
            updateData["content"] = content.value.toString()
            updateData["imageUrl"] = productModel.imageUrl
            updateData["rentAble"] = rentAble.value.toString()

            if (rentAble.value == 0) updateData["rentUser"] = productModel.rentUser
            else if (rentAble.value == 1) updateData["rentUser"] =rentUser.value.toString()

            updateData["createAt"] = productModel.createAt

            firebaseFirestore.collection("product").document(productModel.createAt)
                .update(updateData)
                .addOnSuccessListener {
                    onSuccessEvent.call()
                }
                .addOnFailureListener {
                    onFailureData.value = it
                }
        } else onFailEvent.call()
    }

    fun checkData() : Boolean {
        if (name.value.isNullOrEmpty() || content.value.isNullOrEmpty()) return false
        else return true
    }
}