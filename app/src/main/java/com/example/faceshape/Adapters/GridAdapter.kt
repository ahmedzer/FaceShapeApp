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
import com.example.faceshape.utils.OnItemClickListener

class GridAdapter(var context: Context, var slideList:ArrayList<ItemSlide>, var itemClickListener: OnItemClickListener): RecyclerView.Adapter<GridAdapter.ViewHolder>() {
    inner class ViewHolder(itemV: View): RecyclerView.ViewHolder(itemV),View.OnClickListener{
        val slideImg = itemV.findViewById<ImageView>(R.id.imageView2)
        val slideTxt = itemV.findViewById<TextView>(R.id.textView2)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_grid,parent,false))
    }

    override fun getItemCount(): Int {
        return slideList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.slideImg.setImageResource(slideList[position].img)
        holder.slideTxt.setText(slideList[position].slideTxt)
    }
}