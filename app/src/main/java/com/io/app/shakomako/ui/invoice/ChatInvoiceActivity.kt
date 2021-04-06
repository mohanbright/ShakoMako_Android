package com.io.app.shakomako.ui.invoice

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.io.app.shakomako.R
import com.io.app.shakomako.api.exception.RequiredFieldExceptions
import com.io.app.shakomako.api.pojo.deal.CreateDealData
import com.io.app.shakomako.api.pojo.invoice.InvoiceData
import com.io.app.shakomako.api.pojo.invoice.InvoiceSubmitData
import com.io.app.shakomako.api.pojo.invoice.SaveInvoiceResponse
import com.io.app.shakomako.databinding.ActivityChatInvoiceBinding
import com.io.app.shakomako.databinding.LayoutEditIciFieldBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.map.MapActivity
import com.io.app.shakomako.utils.ContextUtils
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.session.SessionConstants

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ChatInvoiceActivity : DataBindingActivity<ActivityChatInvoiceBinding>(), ViewClickCallback {

    lateinit var viewModel: ChatInvoiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(ChatInvoiceViewModel::class.java)
        init()
    }

    private fun init() {
        dataBinding.viewHandler = this
        dataBinding.viewModel = viewModel

        viewModel.observer.type = intent.getIntExtra(AppConstant.TYPE, 0)

        when (viewModel.observer.type) {
            AppConstant.CREATE_INVOICE -> {
                viewModel.observer.invoiceData =
                    intent.getSerializableExtra(AppConstant.PARCEL_DATA) as InvoiceSubmitData
                viewModel.observer.saveInvoiceData.setData(viewModel.observer.invoiceData)


                viewModel.observer.roomId = intent.getIntExtra(AppConstant.ROOM_ID, 0)
                viewModel.observer.businessId = intent.getIntExtra(AppConstant.BUSINESS_ID, 0)

                Glide.with(dataBinding.root).load(viewModel.observer.saveInvoiceData.productImages)
                    .into(
                        dataBinding.includeChatInvoiceGenerate?.ivProduct!!
                    )
                viewModel.setTotalDealPrice()

            }

            AppConstant.VIEW_INVOICE_BUSINESS -> {
                dataBinding.includeChatInvoiceGenerate?.tvSave?.visibility = GONE
                viewModel.observer.data =
                    intent.getSerializableExtra(AppConstant.PARCEL_DATA) as InvoiceData
                viewModel.observer.saveInvoiceData.setData(viewModel.observer.data)
                Glide.with(dataBinding.root).load(viewModel.observer.saveInvoiceData.productImages)
                    .into(
                        dataBinding.includeChatInvoiceGenerate?.ivProduct!!
                    )
            }

            AppConstant.VIEW_INVOICE_PERSONAL ->{
                viewModel.observer.data =
                    intent.getSerializableExtra(AppConstant.PARCEL_DATA) as InvoiceData
                viewModel.observer.saveInvoiceData.setData(viewModel.observer.data)
                Glide.with(dataBinding.root).load(viewModel.observer.saveInvoiceData.productImages)
                    .into(
                        dataBinding.ivProduct!!
                    )

                Glide.with(dataBinding.root).load(viewModel.observer.data.businessDetails.business_picture)
                    .into(
                        dataBinding.profileImage
                    )
            }
        }

        dataBinding.data = viewModel.observer.saveInvoiceData
    }

    override fun layoutRes(): Int {
        return (R.layout.activity_chat_invoice)
    }

    private fun saveInvoiceData() {
        viewModel.observer.saveInvoiceData.userId = viewModel.observer.userId
        viewModel.saveInvoiceData(apiListener(), viewModel.observer.saveInvoiceData).observe(this,
            Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        viewModel.observer.invoiceId =
                            (response.body ?: SaveInvoiceResponse()).invoice_id
                        createDeal()
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    private fun createDeal() {
        val dealData = CreateDealData(
            viewModel.userSession.getIntValue(SessionConstants.BUSINESS_ID),
            viewModel.observer.saveInvoiceData.dealPrice.toInt(),
            viewModel.observer.saveInvoiceData.invoiceNumber,
            viewModel.observer.saveInvoiceData.productId,
            viewModel.observer.roomId,
            viewModel.userSession.getIntValue(SessionConstants.USER_ID)
        )

        viewModel.createDeal(apiListener(), dealData).observe(this, Observer { response ->
            run {
                if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                    val intent = Intent()
                    intent.putExtra(AppConstant.INVOICE_ID, viewModel.observer.invoiceId)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                } else showToast(
                    response.message ?: resources.getString(R.string.msg_something_went_wrong)
                )
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_close -> {
                finish()
            }

            R.id.iv_back -> {
                onBackPressed()
            }

            R.id.tv_save -> {
                try {
                    viewModel.observer.saveInvoiceData.isValid(this)
                    saveInvoiceData()
                } catch (e: RequiredFieldExceptions) {
                    showToast(e.localizedMessage)
                }
            }

            R.id.iv_edit_delivery_address -> {
                //showEditDialog(1)
                val intent = Intent(getThisActivity(), MapActivity::class.java)
                startActivityForResult(intent, AppConstant.DELIVERY_ADDRESS)
            }

            R.id.iv_edit_quantity -> {
                showEditDialog(2)
            }

            R.id.iv_edit_notes -> {
                showEditDialog(3)
            }

            R.id.iv_product_deal_price -> {
                showEditDialog(4)
            }

            R.id.iv_edit_delivery_fee -> {
                showEditDialog(5)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstant.DELIVERY_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
                val latLng: String? = data?.getStringExtra("result")
                Log.e("TAG", "$latLng")
                val lat = latLng?.split(",")!![0].toDouble()
                val lng = latLng.split(",")[1].toDouble()
                val address = ContextUtils.getAddressFromLatLng(
                    getThisActivity(),
                    lat, lng
                )
                viewModel.observer.saveInvoiceData.deliveryAddress = "${address?.getAddressLine(0)} &CLLocationCoordinate2D(latitude: ${lat}, longitude: $lng"

            }
        }
    }

    private fun showEditDialog(type: Int) {
        val dialog = Dialog(this, R.style.AlertStyleDialog)
        val editIciFieldBinding: LayoutEditIciFieldBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.layout_edit_ici_field,
            null,
            false
        )
        editIciFieldBinding.type = type
        dialogDetails(editIciFieldBinding, type, false)

        editIciFieldBinding.viewHandler = object : ViewClickCallback {
            override fun onClick(v: View) {
                when (v.id) {
                    R.id.iv_cancel -> {
                        dialog.dismiss()
                    }

                    R.id.tv_save -> {
                        dialogDetails(editIciFieldBinding, type, true)
                        dialog.dismiss()
                    }
                }
            }
        }

        dialog.setContentView(editIciFieldBinding.root)
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun dialogDetails(
        editIciFieldBinding: LayoutEditIciFieldBinding,
        type: Int,
        save: Boolean
    ) {
        when (type) {
            1 -> {
                if (save) {
                    viewModel.observer.saveInvoiceData.deliveryAddress =
                        editIciFieldBinding.etEdit.text.toString()
                    return
                }
                editIciFieldBinding.title = "Edit Delivery Address"
                editIciFieldBinding.hint = "Delivery Address"
                editIciFieldBinding.etEdit.setText(if (viewModel.observer.saveInvoiceData.deliveryAddress == null) "" else viewModel.observer.saveInvoiceData.deliveryAddress?.split("&")?.get(0))
            }

            2 -> {
                if (save) {
                    if (editIciFieldBinding.etEdit.text.toString().isEmpty()) {
                        viewModel.observer.saveInvoiceData.quantity = 0
                        return
                    }
                    viewModel.observer.saveInvoiceData.quantity =
                        (editIciFieldBinding.etEdit.text.toString()).toInt()
                    viewModel.setTotalDealPrice()
                    return
                }
                editIciFieldBinding.title = "Edit Quantity"
                editIciFieldBinding.hint = "Quantity"
                editIciFieldBinding.etEdit.setText(viewModel.observer.saveInvoiceData.quantity.toString())
            }

            3 -> {
                if (save) {
                    viewModel.observer.saveInvoiceData.notes =
                        editIciFieldBinding.etEdit.text.toString()
                    return
                }
                editIciFieldBinding.title = "Edit Notes"
                editIciFieldBinding.hint = "Notes"
                editIciFieldBinding.etEdit.setText(viewModel.observer.saveInvoiceData.notes)
            }

            4 -> {
                if (save) {
                    if (editIciFieldBinding.etEdit.text.toString().isEmpty()) {
                        viewModel.observer.saveInvoiceData.dealPrice = "0"
                        return
                    }
                    viewModel.observer.saveInvoiceData.dealPrice =
                        editIciFieldBinding.etEdit.text.toString()
                    viewModel.setTotalDealPrice()
                    return
                }
                editIciFieldBinding.title = "Edit Product Deal Price"
                editIciFieldBinding.hint = "Product Deal Price"
                editIciFieldBinding.etEdit.setText(viewModel.observer.saveInvoiceData.dealPrice)
            }

            5 -> {
                if (save) {
                    if (editIciFieldBinding.etEdit.text.toString().isEmpty()) {
                        viewModel.observer.saveInvoiceData.deliveryFee = "0"
                        return
                    }
                    viewModel.observer.saveInvoiceData.deliveryFee =
                        editIciFieldBinding.etEdit.text.toString()
                    viewModel.setTotalDealPrice()
                    return
                }
                editIciFieldBinding.title = "Edit Delivery Fee"
                editIciFieldBinding.hint = "Delivery Fee"
                editIciFieldBinding.etEdit.setText(viewModel.observer.saveInvoiceData.deliveryFee)
            }
        }
    }



}