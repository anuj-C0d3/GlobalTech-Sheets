package com.example.globaltechsheets

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.globaltechsheets.databinding.OzoneSheetItviewBinding
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Adapterclass(val context: Context,val list:List<Jobsheetdata>):RecyclerView.Adapter<Adapterclass.ViewHolder> (){
    inner class ViewHolder(val binding:OzoneSheetItviewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OzoneSheetItviewBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.customername.text = list[position].customerName
        holder.binding.requestNoview.text = list[position].requestNo
        val db = database.getDatabase(context)
        holder.binding.itviewmobile.text = list[position].mobile
        holder.binding.ivewAddre.text = list[position].address2
        holder.binding.deletejobsheet.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Delete")
                .setIcon(R.drawable.delete_icon)
                .setMessage("Are you sure?")
                .setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, i ->
                    GlobalScope.launch {
                        db.ozonDao().delete(list[position])
                    }
                    dialog.dismiss()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener{dialog,i->
                    dialog.dismiss()
                })
                .show()
                .create()
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context,OzoneJobsheet::class.java)
                val gson = Gson()
            val jsonString = gson.toJson(list[position])

            intent.putExtra("jsonString",jsonString)

            context.startActivity(intent)

        }
    }

}