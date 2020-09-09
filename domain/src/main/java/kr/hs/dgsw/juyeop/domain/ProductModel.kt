package kr.hs.dgsw.juyeop.domain

import java.io.Serializable

class ProductModel (
    val name: String,
    val imageUrl: String,
    val content: String,
    var rentAble: Int,
    var rentUser: String,
    val createAt: String
): Serializable