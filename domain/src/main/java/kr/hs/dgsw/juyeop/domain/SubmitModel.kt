package kr.hs.dgsw.juyeop.domain

import java.io.Serializable

class SubmitModel (
    val name: String,
    val imageUrl: String,
    val reason: String,
    val price: String,
    val content: String,
    val submitUser: String,
    val submitAble: Int,
    val createAt: String
): Serializable