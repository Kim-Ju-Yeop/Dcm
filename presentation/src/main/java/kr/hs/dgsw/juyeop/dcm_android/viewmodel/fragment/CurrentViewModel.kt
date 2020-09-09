package kr.hs.dgsw.juyeop.dcm_android.viewmodel.fragment

import com.google.firebase.firestore.FirebaseFirestore
import kr.hs.dgsw.juyeop.dcm_android.base.viewmodel.BaseViewModel
import kr.hs.dgsw.juyeop.dcm_android.widget.recyclerview.adapter.ProductAdapter
import kr.hs.dgsw.juyeop.dcm_android.widget.recyclerview.adapter.SubmitAdapter
import kr.hs.dgsw.juyeop.domain.ProductModel
import kr.hs.dgsw.juyeop.domain.SubmitModel

class CurrentViewModel : BaseViewModel() {

    val productList = ArrayList<ProductModel>()
    val productAdapter = ProductAdapter()

    val submitList = ArrayList<SubmitModel>()
    val submitAdapter = SubmitAdapter()

    val firebaseFirestore = FirebaseFirestore.getInstance()

    fun lendingProductList() {
        productList.clear()
        firebaseFirestore.collection("product")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (Integer.parseInt(document["rentAble"].toString()) == 2) {
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
                    productAdapter.setList(productList)
                    productAdapter.notifyDataSetChanged()
                }
            }
    }

    fun lendingSubmitList() {
        submitList.clear()
        firebaseFirestore.collection("submit")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (Integer.parseInt(document["submitAble"].toString()) == 0) {
                            submitList.add(
                                SubmitModel(
                                    document["name"].toString(),
                                    document["imageUrl"].toString(),
                                    document["reason"].toString(),
                                    document["price"].toString(),
                                    document["content"].toString(),
                                    document["submitUser"].toString(),
                                    Integer.parseInt( document["submitAble"].toString()),
                                    document["createAt"].toString(),
                                )
                            )
                        }
                    }
                    submitAdapter.setList(submitList)
                    submitAdapter.notifyDataSetChanged()
                }
            }
    }
}