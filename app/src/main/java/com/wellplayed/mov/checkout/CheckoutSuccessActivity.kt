package com.wellplayed.mov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wellplayed.mov.R
import com.wellplayed.mov.home.HomeActivity
import kotlinx.android.synthetic.main.activity_checkout_success.*
import kotlinx.android.synthetic.main.activity_checkout_success.btn_home
import kotlinx.android.synthetic.main.activity_onboarding_tree.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_tiket.*

class CheckoutSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        btn_home.setOnClickListener {
            finishAffinity()

            val intent = Intent(
                this@CheckoutSuccessActivity,
                HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
