package kr.hs.dgsw.juyeop.dcm_android.viewmodel.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.`object`.SharedPreferencesManager
import java.lang.Exception

class AccountViewmodel(
    val context: Context
) : BaseViewModel() {

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val onFailureData = MutableLiveData<Exception>()

    val firebaseFirestore = FirebaseFirestore.getInstance()

    init {
        findUserInfo()
    }

    fun findUserInfo() {
        firebaseFirestore.collection("teacher").document(SharedPreferencesManager.getUserUid(context).toString())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    name.value = task.result!!.get("name").toString()
                    email.value = task.result!!.get("email").toString()
                }
            }
            .addOnFailureListener {
                onFailureData.value = it
            }
    }
}