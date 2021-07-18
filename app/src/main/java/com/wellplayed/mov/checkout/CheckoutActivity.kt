package com.wellplayed.mov.checkout

import android.app.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.wellplayed.mov.R
import com.wellplayed.mov.checkout.adapter.CheckoutAdapter
import com.wellplayed.mov.checkout.model.Checkout
import com.wellplayed.mov.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.Button
import androidx.core.app.NotificationCompat
import com.wellplayed.mov.home.tiket.TiketActivity
import com.wellplayed.mov.model.Film
import kotlinx.android.synthetic.main.activity_checkout.btn_home
import kotlinx.android.synthetic.main.activity_checkout.iv_close
import kotlinx.android.synthetic.main.activity_pilih_bangku.*


class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total:Int = 0

    private lateinit var preferences:Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>
        val data = intent.getParcelableExtra<Film>("datas")

        for (a in dataList.indices){
            total += dataList[a].harga!!.toInt()
        }

        dataList.add(Checkout("Total Harus Dibayar", total.toString()))

        btn_tiket.setOnClickListener {
            val intent = Intent(this@CheckoutActivity,
                CheckoutSuccessActivity::class.java)
            startActivity(intent)

            showNotif(data!!)
        }

        btn_home.setOnClickListener {
            finish()
        }

        iv_close.setOnClickListener {
            finish()
        }

        rc_checkout.layoutManager = LinearLayoutManager(this)
        rc_checkout.adapter = CheckoutAdapter(dataList) {
        }

        if(preferences.getValues("saldo")!!.isNotEmpty()) {
            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            tv_saldo.setText(formatRupiah.format(preferences.getValues("saldo")!!.toDouble()))
            btn_tiket.visibility = View.VISIBLE
            textView3.visibility = View.INVISIBLE

        } else {
            tv_saldo.setText("Rp 0")
            btn_tiket.visibility = View.INVISIBLE
            textView3.visibility = View.VISIBLE
            textView3.text = "Saldo pada e-wallet kamu tidak mencukupi\n" + "untuk melakukan transaksi"
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

    private fun showNotif(datas: Film) {
        val NOTIFICATION_CHANNEL_ID = "channel_mov_notif"
        val context = this.applicationContext
        var notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelName = "mov Notif Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

//        val mIntent = Intent(this, CheckoutSuccessActivity::class.java)
//        val bundle = Bundle()
//        bundle.putString("id", "id_film")
//        mIntent.putExtras(bundle)

        val mIntent = Intent(this, TiketActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("data", datas)
        mIntent.putExtras(bundle)

        val pendingIntent =
            PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.logo_mov)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.logo_notification
                )
            )
            .setTicker("notif mov starting")
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setLights(Color.RED, 3000, 3000)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentTitle("Sukses Terbeli")
            .setContentText("Tiket "+datas.judul+" berhasil kamu dapatkan ")

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(115, builder.build())
    }
}