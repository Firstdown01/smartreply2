package com.example.smartreply2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(var obj: ArrayList<Message>) : RecyclerView.Adapter<Adapter.ViewHolder>() {


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */

    override fun getItemCount(): Int {
        return obj.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(obj.get(position))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: View =
            LayoutInflater.from(parent.context).inflate(R.layout.message, parent, false)
        return ViewHolder(binding)
    }



      class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          var rightview: TextView? = null
          var leftview: TextView? = null
          fun bind(obj: Message) {
              if(!obj.sender){
                  this.rightview=itemView.findViewById(R.id.request_id)
                  rightview?.text = obj.msg
                  rightview?.visibility=View.VISIBLE
                  leftview?.visibility=View.GONE

              }else{
                  this.leftview=itemView.findViewById(R.id.response_id)
                  leftview?.text = obj.msg
                  leftview?.visibility=View.VISIBLE
                  rightview?.visibility=View.GONE
              }

          }
    }



}