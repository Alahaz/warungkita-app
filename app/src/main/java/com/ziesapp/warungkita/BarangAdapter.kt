package com.ziesapp.warungkita

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_barang.view.*

class BarangAdapter(options: FirestoreRecyclerOptions<Barang>) :FirestoreRecyclerAdapter<Barang,BarangAdapter.BarangAdapterViewHolder>(
    options
) {
    class BarangAdapterViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var nama: TextView = itemView.tvNama
        var jumlahBarang:TextView = itemView.tvJumlahBarang
        var satuanBarang:TextView = itemView.tvSatuanBarang

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangAdapterViewHolder {
        return BarangAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_barang,parent,false))
    }

    override fun onBindViewHolder(holder: BarangAdapterViewHolder, position: Int, model: Barang) {
        holder.nama.text = model.nama
        holder.jumlahBarang.text = model.jumlahBarang
        holder.satuanBarang.text = model.satuanBarang
    }
}