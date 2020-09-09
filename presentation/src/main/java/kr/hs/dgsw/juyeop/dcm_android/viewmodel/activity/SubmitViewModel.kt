package kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.SingleLiveEvent
import kr.hs.dgsw.juyeop.domain.SubmitModel
import java.lang.Exception

class SubmitViewModel : BaseViewModel() {

    lateinit var submitModel: SubmitModel

    val name = MutableLiveData<String>()
    val reason = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val submitUser = MutableLiveData<String>()
    val onFailureData = MutableLiveData<Exception>()

    val onBackEvent = SingleLiveEvent<Unit>()
    val onSuccessEvent = SingleLiveEvent<Unit>()

    val firebaseFirestore = FirebaseFirestore.getInstance()

    fun backEvent() {
        onBackEvent.call()
    }

    fun setData() {
        name.value = submitModel.name
        reason.value = submitModel.reason
        price.value = "${submitModel.price}원"
        content.value = submitModel.content

        findSubmitUser()
    }

    fun findSubmitUser() {
        firebaseFirestore.collection("student").document(submitModel.submitUser)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    submitUser.value = task.result?.get("name").toString()
                }
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }

    fun accept() {
        val updateData: MutableMap<String, Any> = HashMap()
        updateData["name"] =  submitModel.name
        updateData["imageUrl"] = submitModel.imageUrl
        updateData["reason"] = submitModel.reason
        updateData["price"] = submitModel.price
        updateData["content"] = submitModel.content
        updateData["submitUser"] = submitModel.submitUser
        updateData["submitAble"] = 1
        updateData["createAt"] = submitModel.createAt

        firebaseFirestore.collection("submit").document(submitModel.createAt)
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
        updateData["name"] =  submitModel.name
        updateData["imageUrl"] = submitModel.imageUrl
        updateData["reason"] = submitModel.reason
        updateData["price"] = submitModel.price
        updateData["content"] = submitModel.content
        updateData["submitUser"] = submitModel.submitUser
        updateData["submitAble"] = 2
        updateData["createAt"] = submitModel.createAt

        firebaseFirestore.collection("submit").document(submitModel.createAt)
            .update(updateData)
            .addOnSuccessListener {
                onSuccessEvent.call()
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }
}