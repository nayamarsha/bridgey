package com.example.bridgey2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.widget.TextView
import android.widget.Toast

class SettingActivity : AppCompatActivity() {
    private lateinit var cardDisplayAppearance: CardView
    private lateinit var cardAboutUs: CardView
    private lateinit var cardLegal: CardView
    private lateinit var cardNotification: CardView
    private lateinit var setBack: ImageView

    private lateinit var groupAppInfo: LinearLayout
    private lateinit var groupLegal: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setBack = findViewById(R.id.setBack)

        // Inisialisasi view
        cardDisplayAppearance = findViewById(R.id.card_DisplayAppearance)
        cardAboutUs = findViewById(R.id.card_aboutUs)
        cardLegal = findViewById(R.id.card_legal)
        cardNotification = findViewById(R.id.card_notification)


        groupAppInfo = findViewById(R.id.groupAppInfo)
        groupLegal = findViewById(R.id.groupLegal)
        val iconAboutUs = findViewById<ImageView>(R.id.icon_aboutUs)
        val iconlegal = findViewById<ImageView>(R.id.icon_legal)

        cardAboutUs.setOnClickListener {
            toggleGroup(groupAppInfo, iconAboutUs)
        }

        // Toggle Legal
        cardLegal.setOnClickListener {
            toggleGroup(groupLegal, iconlegal)
        }

        // Tambahkan aksi lainnya jika dibutuhkan
        findViewById<View>(R.id.itemAboutUs).setOnClickListener {
            // startActivity(Intent(this, AboutUsActivity::class.java))
        }

        findViewById<View>(R.id.card_DisplayAppearance).setOnClickListener {
            // startActivity(Intent(this, DisplaySettingsActivity::class.java))
        }

        findViewById<View>(R.id.card_notification).setOnClickListener {
            // startActivity(Intent(this, NotificationSettingsActivity::class.java))
        }

        setBack.setOnClickListener { finish() }
    }



    private fun toggleGroup(group: View, icon: ImageView) {
        val expanded = group.visibility == View.VISIBLE
        group.visibility = if (expanded) View.GONE else View.VISIBLE
        icon.animate().rotation(if (expanded) 0f else 90f).setDuration(200).start()
    }


}
