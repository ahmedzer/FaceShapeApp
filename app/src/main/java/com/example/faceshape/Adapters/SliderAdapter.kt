package com.example.faceshape.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.faceshape.Entities.ItemSlide
import com.example.faceshape.R
import org.w3c.dom.Text


class SliderAdapter(var context: Context,var slideList:ArrayList<ItemSlide>):RecyclerView.Adapter<SliderAdapter.ViewHolder>() {
    class ViewHolder(itemV: View):RecyclerView.ViewHolder(itemV){
        val slideImg = itemV.findViewById<ImageView>(R.id.imageView)
        val slideTxt = itemV.findViewById<TextView>(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
    }

    override fun getItemCount(): Int {
        return slideList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.slideImg.setImageResource(slideList[position].img)
        holder.slideTxt.setText(slideList[position].slideTxt)
    }
}