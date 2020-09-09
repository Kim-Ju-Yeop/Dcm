package kr.hs.dgsw.juyeop.dcm_android.viewmodel.activity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.SingleLiveEvent
import kr.hs.dgsw.juyeop.dcm_android.widget.`object`.SharedPreferencesManager

class SignInViewModel(
    private val context: Context
) : BaseViewModel() {

    val onCompleteEvent = SingleLiveEvent<Unit>()
    val onCompleteErrorEvent = SingleLiveEvent<Unit>()
    val onSignUpEvent = SingleLiveEvent<Unit>()

    val email = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun signIn() {
        setIsLoadingTrue()
        firebaseAuth.signInWithEmailAndPassword(email.value.toString(), pw.value.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setIsLoadingFalse()

                    SharedPreferencesManager.setUserUid(context, task.result!!.user!!.uid)
                    onCompleteEvent.call()
                } else {
                    setIsLoadingFalse()
                    onCompleteErrorEvent.call()
                }
            }
    }

    fun signUp() {
        onSignUpEvent.call()
    }
}