package kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.SingleLiveEvent
import kr.hs.dgsw.juyeop.dcm_android.widget.`object`.ImageManager
import kr.hs.dgsw.juyeop.dcm_android.widget.extension.dateFormat
import kr.hs.dgsw.juyeop.domain.ProductModel
import java.lang.Exception
import java.util.*

class AddViewModel : BaseViewModel() {

    var imageByteArray = ByteArray(0)

    val name = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val rentAble = MutableLiveData<Int>()
    val rentUser = MutableLiveData<String>()
    val createAt = MutableLiveData<String>()
    val onFailureData = MutableLiveData<Exception>()

    val onBackEvent = SingleLiveEvent<Unit>()
    val onCameraEvent = SingleLiveEvent<Unit>()
    val onFailureEvent = SingleLiveEvent<Unit>()
    val onSuccessEvent = SingleLiveEvent<Unit>()

    val firebaseFirestore = FirebaseFirestore.getInstance()

    fun cameraEvent() {
        onCameraEvent.call()
    }
    fun backEvent() {
        onBackEvent.call()
    }

    fun addEvent() {
        if (checkData()) {
            val imageName = Date().dateFormat()
            val mStorageRef = FirebaseStorage.getInstance().reference
            val riverRef = mStorageRef.child("product/$imageName")

            riverRef.putBytes(imageByteArray)
                .addOnSuccessListener { task ->
                    task.storage.downloadUrl.addOnSuccessListener { uri ->
                        createAt.value = task.storage.name
                        imageUrl.value = uri.toString()
                        addProduct()
                    }
                }
                .addOnFailureListener {
                    onFailureData.value = it
                }
        } else onFailureEvent.call()
    }

    fun addProduct() {
        rentAble.value = 1
        rentUser.value = ""

        val productModel = ProductModel(name.value.toString(), imageUrl.value.toString(), content.value.toString(),
            rentAble.value!!, rentUser.value.toString(), createAt.value.toString())

        firebaseFirestore.collection("product").document(createAt.value.toString())
            .set(productModel)
            .addOnSuccessListener {
                ImageManager.byteArray = ByteArray(0)
                onSuccessEvent.call()
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }

    fun checkData() : Boolean {
        if (name.value.isNullOrEmpty() || content.value.isNullOrEmpty()) return false
        else if (imageByteArray.isNotEmpty()) return true
        return false
    }
}