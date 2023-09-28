package com.teststa.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teststa.base.BaseRecyclerViewAdapter
import com.teststa.data.Karyawan
import com.teststa.data.dateToUiFormat
import com.teststa.databinding.ItemKaryawanBinding

class KaryawanViewHolder(
    private val binding: ItemKaryawanBinding
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(item: Karyawan) {

        binding.apply {
            idTxt.text = item.idKaryawan
            nmKaryawanTxt.text = item.nmKaryawan
            tglMskKrjTxt.text = item.tglMasukKerja.dateToUiFormat()
            usiaTxt.text = item.usia.toString()
        }
    }
}

class KaryawanAdapter : BaseRecyclerViewAdapter<KaryawanViewHolder, Int, Karyawan>() {

    var onClickItem: ((View, Karyawan) -> Unit)? = null
    private var selectedItemView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KaryawanViewHolder =
        KaryawanViewHolder(
            ItemKaryawanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: KaryawanViewHolder, position: Int) {
        val item = oldList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { view ->
            onClickItem?.let { it(view, item) }
            selectedItemView?.setBackgroundColor(Color.TRANSPARENT)
            view.setBackgroundColor(Color.YELLOW)
            selectedItemView = view
        }
    }

}