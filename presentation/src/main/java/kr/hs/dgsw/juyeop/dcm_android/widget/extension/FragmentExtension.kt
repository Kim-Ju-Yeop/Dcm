package kr.hs.dgsw.juyeop.dcm_android.widget.extension

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.startActivityNoFinish(context: Context, activity: Class<*>) {
    startActivity(Intent(context, activity).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
}

fun Fragment.startActivityWithExtraNoFinish(intent: Intent) {
    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
}

fun Fragment.shortToastMessage(message: String) {
    Toast.makeText(context!!.applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToastMessage(message: String) {
    Toast.makeText(context!!.applicationContext, message, Toast.LENGTH_LONG).show()
}