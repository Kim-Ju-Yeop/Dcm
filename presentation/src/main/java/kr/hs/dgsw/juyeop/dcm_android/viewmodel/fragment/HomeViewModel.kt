package kr.hs.dgsw.juyeop.dcm_android.viewmodel.fragment

import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.SingleLiveEvent
import kr.hs.dgsw.juyeop.dcm_android.widget.recyclerview.adapter.ProductAdapter
import kr.hs.dgsw.juyeop.domain.ProductModel
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel : BaseViewModel() {

    val productList = ArrayList<ProductModel>()
    val productAdapter = ProductAdapter()

    val onAddEvent = SingleLiveEvent<Unit>()

    val firebaseFirestore = FirebaseFirestore.getInstance()

    init {
        entireList()
    }

    fun addEvent() {
        onAddEvent.call()
    }

    fun entireList() {
        productList.clear()
        firebaseFirestore.collection("product")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        productList.add(
                            ProductModel(
                                document["name"].toString(),
                                document["imageUrl"].toString(),
                                document["content"].toString(),
                                Integer.parseInt(document["rentAble"].toString()),
                                document["rentUser"].toString(),
                                document["createAt"].toString()))
                    }
                    productList.reverse()
                    productAdapter.setList(productList)
                    productAdapter.notifyDataSetChanged()
                }
            }
    }

    // 대여 중 [rentAble == 0]
    fun lendingList() {
        productList.clear()
        firebaseFirestore.collection("product")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (Integer.parseInt(document["rentAble"].toString()) == 0) {
                            productList.add(
                                ProductModel(
                                    document["name"].toString(),
                                    document["imageUrl"].toString(),
                                    document["content"].toString(),
                                    Integer.parseInt(document["rentAble"].toString()),
                                    document["rentUser"].toString(),
                                    document["createAt"].toString()
                                )
                            )
                        }
                    }
                    productList.reverse()
                    productAdapter.setList(productList)
                    productAdapter.notifyDataSetChanged()
                }

            }
    }

    // 대여 가능 [rentAble == 1]
    fun rentableList() {
        productList.clear()
        firebaseFirestore.collection("product")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (Integer.parseInt(document["rentAble"].toString()) == 1) {
                            productList.add(
                                ProductModel(
                                    document["name"].toString(),
                                    document["imageUrl"].toString(),
                                    document["content"].toString(),
                                    Integer.parseInt(document["rentAble"].toString()),
                                    document["rentUser"].toString(),
                                    document["createAt"].toString()
                                )
                            )
                        }
                    }
                    productList.reverse()
                    productAdapter.setList(productList)
                    productAdapter.notifyDataSetChanged()
                }
            }
    }
}