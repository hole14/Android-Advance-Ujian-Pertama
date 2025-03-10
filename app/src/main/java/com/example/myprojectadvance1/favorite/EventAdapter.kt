package com.example.myprojectadvance1.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myprojectadvance1.R
import com.example.myprojectadvance1.data.local.entity.EventEntity
import com.example.myprojectadvance1.databinding.ItemEventBinding

class EventAdapter(private val onBookmarkClick: (EventEntity) -> Unit): ListAdapter<EventEntity, EventAdapter.MyViewHolder>(DIFF_CALLBACK){
    class MyViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(event: EventEntity) {
            binding.tvItemTitle.text = event.name
            binding.tvItemPublishedDate.text = event.beginTime + " - " + event.endTime
            binding.tvItemSummary.text = event.summary
            Glide.with(itemView.context)
                .load(event.mediaCover)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(binding.imgPoster)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DeskripsiFavoriteActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        val ivBookmark = holder.binding.ivBookmark
        if (event.isFavorited) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_favorite_black_change))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_favorite_black))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(event)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<EventEntity> =
            object : DiffUtil.ItemCallback<EventEntity>() {
                override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }


}