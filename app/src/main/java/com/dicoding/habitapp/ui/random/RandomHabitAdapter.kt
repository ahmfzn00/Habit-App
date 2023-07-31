package com.dicoding.habitapp.ui.random

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //TODO 14 : Create view and bind data to item view
        val pagerTitle = itemView.findViewById<TextView>(R.id.pager_tv_title)
        val pagerStartTime = itemView.findViewById<TextView>(R.id.pager_tv_start_time)
        val pagerMinutes = itemView.findViewById<TextView>(R.id.page_tv_minutes)
        val imgPager = itemView.findViewById<ImageView>(R.id.item_priority_level)
        val btnCountDown = itemView.findViewById<Button>(R.id.btn_open_count_down)

        private fun convertLongToTime(minutes: Long): String {
            val hours = minutes / 60
            val mins = minutes % 60
            return String.format("%02d:%02d", hours, mins)
        }

        fun bind(pageType: PageType, pageData: Habit) {
            pagerTitle.text = pageData.title ?: return
            pagerStartTime.text = pageData.startTime ?: return
            pagerMinutes.text = convertLongToTime(pageData.minutesFocus) ?:return
            when(pageType.name){
                "HIGH" -> {
                    imgPager.setImageResource(R.drawable.ic_priority_high) ?: return
                }

                "MEDIUM" -> {
                    imgPager.setImageResource(R.drawable.ic_priority_medium) ?: return
                }

                "LOW" -> {
                    imgPager.setImageResource(R.drawable.ic_priority_low) ?: return
                }
            }

            btnCountDown.setOnClickListener {
                onClick(pageData)
            }
        }
    }
}
