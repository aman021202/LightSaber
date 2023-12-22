package com.example.lightsaber


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ErrorAdapter(
    private val listOfError:List<SensorData>
):RecyclerView.Adapter<ErrorAdapter.ErrorViewHolder>() {
    class ErrorViewHolder(view:View):RecyclerView.ViewHolder(view){
        val name:TextView=view.findViewById(R.id.tv1)
        val age:TextView=view.findViewById(R.id.tv2)
        val email:TextView=view.findViewById(R.id.tv3)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ErrorViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ErrorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfError.size
    }

    override fun onBindViewHolder(holder: ErrorViewHolder, position: Int) {
        holder.name.text=listOfError[position].name
        holder.age.text=listOfError[position].age.toString()
        holder.email.text=listOfError[position].email

    }
}