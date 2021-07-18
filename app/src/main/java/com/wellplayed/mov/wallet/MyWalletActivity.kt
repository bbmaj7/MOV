package com.wellplayed.mov.wallet

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wellplayed.mov.R
import com.wellplayed.mov.wallet.adapter.WalletAdapter
import com.wellplayed.mov.wallet.model.Wallet
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.activity_my_wallet.iv_close


class MyWalletActivity : AppCompatActivity() {

    private var dataList = ArrayList<Wallet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        dataList.add(
            Wallet(
                "A Quiet Place",
                "Rabu 14 Jul, 2020",
                140000.0,
                "0"
            )
        )
        dataList.add(
            Wallet(
                "Black Widow",
                "Rabu 14 Jul, 2020",
                140000.0,
                "0"
            )
        )
        dataList.add(
            Wallet(
                "Fast & Furious 9",
                "Rabu 14 Jul, 2020",
                140000.0,
                "0"
            )
        )
        dataList.add(
            Wallet(
                "The Conjuring III",
                "Rabu 14 Jul, 2020",
                140000.0,
                "0"
            )
        )

        rv_transaksi.layoutManager = LinearLayoutManager(this)
        rv_transaksi.adapter = WalletAdapter(dataList){

        }

        btn_top_up.setOnClickListener {
            startActivity(Intent(this, MyWalletTopUpActivity::class.java))
        }

        iv_close.setOnClickListener {
            finish()
        }
    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)

        val btnClose = dialog.findViewById(R.id.iv_close) as Button
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
