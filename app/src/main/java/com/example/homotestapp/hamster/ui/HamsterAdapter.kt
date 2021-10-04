package com.example.homotestapp.hamster.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.homotestapp.R
import com.example.homotestapp.hamster.data.HamsterModel
import java.util.*


class HamsterAdapter(val itemClickListener: OnItemClickListener): RecyclerView.Adapter<HamsterAdapter.HomeViewHolder>(){

    private var data : ArrayList<HamsterModel>?=null

    fun setData(list: ArrayList<HamsterModel>){
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item, itemClickListener)
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(item: HamsterModel?, clickListener: OnItemClickListener) {
            itemView.findViewById<ImageView>(R.id.share).setOnClickListener{
                clickListener.shareHamster(itemView)
            }

            if (item?.pinned==true) itemView.findViewById<ImageView>(R.id.star).setImageResource(R.drawable.star_full)
            else itemView.findViewById<ImageView>(R.id.star).setImageResource(R.drawable.star)

            itemView.findViewById<ImageView>(R.id.star).setOnClickListener{
                if (item?.pinned==true) {
                   clickListener.swapHamsterDown(item)
                }
                else {
                    clickListener.swapHamsterUp(item)
                }
            }

            itemView.setOnClickListener {
                clickListener.onItemClicked(item)
            }

            itemView.findViewById<TextView>(R.id.title).text = item?.title

            Glide
                .with(itemView)
                .load(item?.image)
                .error(R.drawable.hamster)
                .fallback(R.drawable.hamster)
                .transform(RoundedCorners(25))
                .into(itemView.findViewById<ImageView>(R.id.image));
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(item: HamsterModel?)
        fun shareHamster(view: View)
        fun swapHamsterUp(item: HamsterModel?)
        fun swapHamsterDown(item: HamsterModel?)
    }
}