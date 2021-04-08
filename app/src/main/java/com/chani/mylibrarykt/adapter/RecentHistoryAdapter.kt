package com.chani.mylibrarykt.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.data.repository.local.entity.History
import com.chani.mylibrarykt.databinding.ItemHistoryBinding
import com.chani.mylibrarykt.util.AppLog
import com.chani.mylibrarykt.viewmodel.RecentHistoryViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class RecentHistoryAdapter(
    private val histories: List<History>,
    private val recentHistoryViewModel: RecentHistoryViewModel
) : RecyclerView.Adapter<RecentHistoryAdapter.RecentHistoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentHistoryHolder {
        return RecentHistoryHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecentHistoryHolder, position: Int) {
        holder.bind(histories[position])
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    inner class RecentHistoryHolder(
        private val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) = with(binding) {
            val format = SimpleDateFormat("MM-dd", Locale.getDefault())
            val time = format.format(Date(history.timestamp))
            AppLog.d("time = $time")

            if (File(history.coverImgPath).exists()) {
                val bmp = BitmapFactory.decodeFile(history.coverImgPath)
                dateIconImg.setImageBitmap(bmp)
            }
        }
    }
}