package com.vision.betting.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vision.betting.R
import com.vision.betting.models.RvModel
import kotlinx.android.synthetic.main.item.view.*

class MyAdapter(var context: Context, var arrayList: ArrayList<RvModel>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvLeagueName.text = arrayList[position].leagues
        holder.tvMatchName.text = arrayList[position].matchName
    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var tvLeagueName: TextView = itemView.tvLeagueName
        var tvMatchName: TextView = itemView.tvMatchName
    }

}