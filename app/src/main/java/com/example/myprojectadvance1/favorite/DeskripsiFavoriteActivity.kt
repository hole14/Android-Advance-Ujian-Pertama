package com.example.myprojectadvance1.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myprojectadvance1.MainActivity
import com.example.myprojectadvance1.R
import com.example.myprojectadvance1.data.local.entity.EventEntity
import com.example.myprojectadvance1.databinding.ActivityDeskripsiFavoriteBinding
import com.example.myprojectadvance1.ui.FavoriteFragment

class DeskripsiFavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeskripsiFavoriteBinding
    private val onBookmarkClick: EventEntity? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeskripsiFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val event = intent.getParcelableExtra<EventEntity>(EXTRA_EVENT)
        val judul = binding.tvTitle
        val deskripsi = binding.tvDeskripsi
        val tanggal = binding.tvDate
        val image = binding.deskripsiEvent
        val kategori = binding.tvCategory
        val quota = binding.tvQouta
        val kota = binding.tvCity

        val back = binding.ciKembali
        val favorite = binding.ciFavorite

        judul.text = event?.name
        deskripsi.text = event?.description
        tanggal.text = event?.beginTime + " - " + event?.endTime
        kategori.text = event?.category
        quota.text = "Qouta " + event?.quota.toString() + " Orang   Terdaftar " + event?.registrants.toString() + " Orang"
        kota.text = event?.cityName

        Glide.with(this)
            .load(event?.imageLogo)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(image)

        back.setOnClickListener {
            val sourcePage = intent.getStringExtra(SOURCE_PAGE)
            val target = when (sourcePage) {
                "favorite" -> FavoriteFragment::class.java
                else -> MainActivity::class.java
            }
            startActivity(Intent(this, target))
            finish()
        }

        if (event?.isFavorited == true){
            favorite.setImageResource(R.drawable.ic_favorite_change)
        }else{
            favorite.setImageResource(R.drawable.ic_favorite)
        }
        favorite.setOnClickListener {
            onBookmarkClick
        }
    }

    companion object {
        const val EXTRA_EVENT = "extra_event"
        const val SOURCE_PAGE = "source_page"
    }
}