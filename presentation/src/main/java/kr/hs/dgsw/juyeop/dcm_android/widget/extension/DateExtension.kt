package kr.hs.dgsw.juyeop.dcm_android.widget.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.dateFormat(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm:Ss", Locale.getDefault())
    return simpleDateFormat.format(this)
}