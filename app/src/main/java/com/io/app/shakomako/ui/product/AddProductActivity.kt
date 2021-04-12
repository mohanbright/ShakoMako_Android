package com.io.app.shakomako.ui.product

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.io.app.shakomako.R
import com.io.app.shakomako.api.exception.RequiredFieldExceptions
import com.io.app.shakomako.api.pojo.shop.BusinessProducts
import com.io.app.shakomako.databinding.ActivityAddProductBinding
import com.io.app.shakomako.helper.callback.DataItemCallBack
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.picker.listener.OnItemSelectedListener
import kotlinx.android.synthetic.main.activity_add_product.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class AddProductActivity : DataBindingActivity<ActivityAddProductBinding>(), ViewClickCallback {

    private lateinit var viewModel: AddProductViewModel
    private var map: HashMap<Int, String> = HashMap()
    private var hashTagList: ArrayList<String> = ArrayList()
    private lateinit var categoryList: Array<String>
    private var isFirstTime = true

    override fun layoutRes(): Int {
        return R.layout.activity_add_product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = bindViewModel {
        }

        init()
    }

    private fun init() {
        categoryList = resources.getStringArray(R.array.product_category)

        openSpinner()

        dataBinding.hashtagCount = "0"
        dataBinding.viewModel = viewModel
        dataBinding.viewHandler = this
        viewModel.addProductObserver.type = intent.getIntExtra(AppConstant.TYPE, 0)
        if (viewModel.addProductObserver.type == AppConstant.EDIT_PRODUCT) {
            viewModel.addProductObserver.businessProduct = intent.getSerializableExtra(
                AppConstant.PARCEL_DATA
            ) as BusinessProducts
            viewModel.addProductObserver.productRequest.setData(
                viewModel.addProductObserver.businessProduct
            )

            for (path in viewModel.addProductObserver.productRequest.productImages.indices) {
                map[path] = viewModel.addProductObserver.productRequest.productImages[path]
            }
            checkHashTag()

            loadServerImage(ArrayList(map.values))

            Log.e("TAG", map.size.toString())
        }


        viewModel.addProductObserver.businessId = intent.getIntExtra(AppConstant.BUSINESS_ID, 0)

        dataBinding.tvBio.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkHashTag()
                hideKeyboard()
                return@OnEditorActionListener true
            }

            false
        })

        uploadObserver()
    }

    private fun checkHashTag() {
        hashTagList.clear()
        val regexPattern = "(#\\w+)"
        val p: Pattern = Pattern.compile(regexPattern)
        val m: Matcher =
            p.matcher(viewModel.addProductObserver.productRequest.productDescription)
        while (m.find()) {
            hashTagList.add(m.group())
        }

        dataBinding.hashtagCount = hashTagList.size.toString()
    }

    private fun loadServerImage(paths: ArrayList<String>) {
        for (position in 0..paths.size)
            when (position) {
                0 -> {
                    loadImage(paths[position], dataBinding.ivFirstImage!!)
                }
                1 -> {
                    loadImage(paths[position], dataBinding.ivSecondImage!!)
                }
                2 -> {
                    loadImage(paths[position], dataBinding.ivThirdImage!!)
                }
                3 -> {
                    loadImage(paths[position], dataBinding.ivForthImage!!)
                }
                4 -> {
                    loadImage(paths[position], dataBinding.ivFifthImage!!)
                }
            }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                onBackPressed()
            }

            R.id.ll_first_image -> {
                openImagePicker(1)
            }
            R.id.iv_second_image -> {
                openImagePicker(2)
            }
            R.id.iv_third_image -> {
                openImagePicker(3)
            }
            R.id.iv_forth_image -> {
                openImagePicker(4)
            }
            R.id.iv_fifth_image -> {
                openImagePicker(5)
            }

            R.id.tv_submit -> {
                if (viewModel.addProductObserver.type != AppConstant.EDIT_PRODUCT) {
                    addProduct()
                } else updateImage()
            }

            R.id.tv_category -> {
                dataBinding.spinnerCategory?.performClick()
                isFirstTime = false

            }
        }

    }

    private fun updateProduct() {
        viewModel.addProductObserver.productRequest.productId =
            intent.getIntExtra(AppConstant.PRODUCT_ID, 0)
        viewModel.addProductObserver.productRequest.productImages = ArrayList(map.values)
        try {
            viewModel.addProductObserver.productRequest.isInputValid(
                getThisActivity(),
                isUpdate = true
            )
            viewModel.updateProduct(viewModel.addProductObserver.productRequest, apiListener())
                .observe(this, Observer { response ->
                    run {
                        if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                            finish()
                        } else showToast(
                            response.message ?: resources
                                .getString(R.string.msg_something_went_wrong)
                        )
                    }
                })
        } catch (e: RequiredFieldExceptions) {
            showToast(e.localizedMessage ?: "")
        }

    }

    private fun updateImage() {
        val list = ArrayList<Int>()
        list.clear()
        for (path in 0 until map.size) {
            if (map[path].toString()
                    .contains("http://ec2-3-142-205-39.us-east-2.compute.amazonaws.com:3000/")
            ) {
                list.add(path)
                if (list.size == 5) {
                    updateProduct()
                }
            } else {
                viewModel.upload(
                    apiListener(),
                    map[path].toString()
                ).observe(this, Observer { response ->
                    run {
                        if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                            map[path] = response.body?.image!!
                            list.add(path)
                            if (list.size == 5) {
                                updateProduct()
                            }

                        } else BaseUtils.hideProgressbar()
                    }
                })
            }
        }
    }

    private fun addProduct() {
        viewModel.addProductObserver.productRequest.businessid =
            viewModel.addProductObserver.businessId
        viewModel.addProductObserver.productRequest.productHashTags = hashTagList
        viewModel.addProductObserver.productRequest.productImages = ArrayList(map.values)
        try {
            viewModel.addProductObserver.productRequest.isInputValid(
                getThisActivity(),
                isUpdate = false
            )
            uploadProductImage()
        } catch (e: RequiredFieldExceptions) {
            showToast(e.localizedMessage ?: "")
        }

    }

    private fun uploadProductImage() {
        val list = ArrayList<Int>()
        list.clear()
        viewModel.addProductObserver.productRequest.productImages = ArrayList()
        BaseUtils.showProgressbar(this)
        for (path in 0 until map.size) {
            Log.e("ImagesTag", "images " + map[path].toString())
            viewModel.upload(
                apiListener(),
                map[path].toString()
            ).observe(this, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        map[path] = response.body?.image!!
                        list.add(path)
                        if (list.size == 5) {
                            viewModel.imageUploaded.value = true
                        }
                        Log.e("ImagesTag", "Uploaded Images " + map[path].toString())
                        (viewModel.addProductObserver.productRequest.productImages as ArrayList).add(
                            map[path].toString()
                        )
                    } else BaseUtils.hideProgressbar()
                }
            })
        }
    }

    private fun uploadObserver() {
        viewModel.imageUploaded.observe(this, Observer { hasUploaded ->
            run {
                if (hasUploaded) {
                    BaseUtils.hideProgressbar()
                    viewModel.addProduct(
                        viewModel.addProductObserver.productRequest,
                        apiListener()
                    )
                        .observe(this, Observer { response ->
                            run {
                                if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                                    finish()
                                } else showToast(
                                    response.message
                                        ?: resources.getString(R.string.msg_something_went_wrong)
                                )
                            }
                        })

                }
            }
        })

    }

    private fun openImagePicker(type: Int) {
        openSingleImagePicker(object : DataItemCallBack<Uri, Int> {
            override fun onItemData(t: Uri?, r: Int?) {
                when (type) {
                    1 -> {
                        loadImage(t?.path!!, dataBinding.ivFirstImage)
                        map[0] = t.path!!
                    }
                    2 -> {
                        loadImage(t?.path!!, dataBinding.ivSecondImage)
                        map[1] = t.path!!
                    }
                    3 -> {
                        loadImage(t?.path!!, dataBinding.ivThirdImage)
                        map[2] = t.path!!
                    }
                    4 -> {
                        loadImage(t?.path!!, dataBinding.ivForthImage)
                        map[3] = t.path!!
                    }
                    5 -> {
                        loadImage(t?.path!!, dataBinding.ivFifthImage)
                        map[4] = t.path!!
                    }
                }
            }
        })
    }

    private fun loadImage(path: String, view: AppCompatImageView) {
        Glide.with(dataBinding.root).load(path).into(view)
    }

    private fun openSpinner() {
        val adapter = ArrayAdapter(
            this,
            R.layout.layout_product_category_spinner,
            categoryList
        )
        dataBinding.spinnerCategory?.adapter = adapter

        dataBinding.spinnerCategory?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (isFirstTime) return
                    (view as TextView).text = ""
                    viewModel.addProductObserver.productRequest.productCategory =
                        categoryList[position]
                    Log.e("TAG", categoryList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }
}
























