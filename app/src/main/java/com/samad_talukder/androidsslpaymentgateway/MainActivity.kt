package com.samad_talukder.androidsslpaymentgateway

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.sslcommerz.library.payment.model.datafield.MandatoryFieldModel
import com.sslcommerz.library.payment.model.dataset.TransactionInfo
import com.sslcommerz.library.payment.model.util.ErrorKeys
import com.sslcommerz.library.payment.model.util.SdkCategory
import com.sslcommerz.library.payment.viewmodel.listener.OnPaymentResultListener
import com.sslcommerz.library.payment.viewmodel.management.PayUsingSSLCommerz
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val amount = "100"
        val currencyType = "BDT"
        val sdkType = "TESTBOX" // LIVE
        val storeID = "testbox"
        val storePassword = "qwerty"


        tvPayNow.setOnClickListener { goToSSLPaymentGatewayPage(storeID, storePassword, sdkType, currencyType, amount) }
    }

    private fun goToSSLPaymentGatewayPage(storeID: String, storePassword: String, sdkType: String, currencyType: String, amount: String) {
        try {

            val mandatoryFieldModel = MandatoryFieldModel(storeID, storePassword, amount, "" + System.currentTimeMillis().toString(), currencyType, sdkType, SdkCategory.BANK_LIST)

            PayUsingSSLCommerz.getInstance().setData(this, mandatoryFieldModel, object : OnPaymentResultListener {

                override fun transactionSuccess(transactionInfo: TransactionInfo?) {

                    if (transactionInfo!!.riskLevel == "0") {
                        Toast.makeText(this@MainActivity, "Your Payment Has Been Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Your Transaction In Risk" + transactionInfo.riskTitle, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun transactionFail(message: String?) {
                    Toast.makeText(this@MainActivity, "Your Transaction Has Been Failed", Toast.LENGTH_SHORT).show()
                }

                override fun error(errorCode: Int) {

                    when (errorCode) {

                        ErrorKeys.USER_INPUT_ERROR -> Toast.makeText(this@MainActivity, "USER INPUT ERROR", Toast.LENGTH_SHORT).show()

                        ErrorKeys.INTERNET_CONNECTION_ERROR -> Toast.makeText(this@MainActivity, "INTERNET CONNECTION ERROR", Toast.LENGTH_SHORT).show()

                        ErrorKeys.DATA_PARSING_ERROR -> Toast.makeText(this@MainActivity, "DATA PARSING ERROR", Toast.LENGTH_SHORT).show()

                        ErrorKeys.CANCEL_TRANSACTION_ERROR -> Toast.makeText(this@MainActivity, "CANCEL TRANSACTION ERROR", Toast.LENGTH_SHORT).show()

                        ErrorKeys.SERVER_ERROR -> Toast.makeText(this@MainActivity, "SERVER ERROR", Toast.LENGTH_SHORT).show()

                        ErrorKeys.NETWORK_ERROR -> Toast.makeText(this@MainActivity, "NETWORK ERROR", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        } catch (ex: Exception) {
            Log.e("PaymentError", "Exception: $ex")
        }
    }
}