package kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.SingleLiveEvent
import kr.hs.dgsw.juyeop.dcm_android.widget.`object`.SharedPreferencesManager
import kr.hs.dgsw.juyeop.domain.ProductModel
import java.lang.Exception

class DetailViewModel(
    val context: Context
) : BaseViewModel() {

    lateinit var productModel: ProductModel

    val rentAble = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val rentUser = MutableLiveData<String>()
    val onFailureData = MutableLiveData<Exception>()

    val onBackEvent = SingleLiveEvent<Unit>()
    val onSuccessEvent = SingleLiveEvent<Unit>()
    val onModifyEvent = SingleLiveEvent<Unit>()

    val firebaseFirestore = FirebaseFirestore.getInstance()

    fun setUpData() {
        name.value = productModel.name
        content.value = productModel.content

        when (productModel.rentAble) {
            0 -> rentAble.value = "대여 중"
            1 -> rentAble.value = "대여 가능"
            2 -> rentAble.value = "승인 대기 중"
        }
    }

    fun backEvent() {
        onBackEvent.call()
    }
    fun modify() {
        onModifyEvent.call()
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

    fun accept() {
        val updateData: MutableMap<String, Any> = HashMap()
        updateData["name"] =  productModel.name
        updateData["imageUrl"] = productModel.imageUrl
        updateData["content"] = productModel.content
        updateData["rentAble"] = 0
        updateData["rentUser"] = productModel.rentUser
        updateData["createAt"] = productModel.createAt

        firebaseFirestore.collection("product").document(productModel.createAt)
            .update(updateData)
            .addOnSuccessListener {
                onSuccessEvent.call()
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }

    fun cancel() {
        val updateData: MutableMap<String, Any> = HashMap()
        updateData["name"] =  productModel.name
        updateData["imageUrl"] = productModel.imageUrl
        updateData["content"] = productModel.content
        updateData["rentAble"] = 1
        updateData["rentUser"] = ""
        updateData["createAt"] = productModel.createAt

        firebaseFirestore.collection("product").document(productModel.createAt)
            .update(updateData)
            .addOnSuccessListener {
                onSuccessEvent.call()
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }
}