package com.example.baimsdailyforecast.ui.home.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.baimsdailyforecast.databinding.ListItemBinding
import com.example.baimsdailyforecast.models.WeatherData
import com.example.baimsdailyforecast.utils.Extensions.toCelsiusString
import com.example.baimsdailyforecast.utils.Utils.getDayNameFromDate
import com.example.baimsdailyforecast.utils.Utils.getImageCondition


class DaysAdapter(private val  list: List<WeatherData>,val context: Context) :
        RecyclerView.Adapter<DaysAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListItemBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    @RequiresApi(Build.VERSION_CODES.O) override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.binding.temp.text = list[position].main.temp.toCelsiusString()
        holder.binding.image
            .setImageDrawable(AppCompatResources.getDrawable(context,getImageCondition(list[position])))



        holder.binding.dayName.text = getDayNameFromDate(list[position].dt_txt)

    }

    override fun getItemCount(): Int = list.size



}
