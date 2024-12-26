package com.example.kpu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kpu.data.VoterEntity

class VoterAdapter(
    private val context: Context,
    private val voters: List<VoterEntity>,
    private val onItemClicked: (VoterEntity) -> Unit, // Edit
    private val onDeleteClicked: (VoterEntity) -> Unit, // Delete
    private val onEyeClicked: (VoterEntity) -> Unit // Eye (Detail)
) : RecyclerView.Adapter<VoterAdapter.VoterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_voter, parent, false)
        return VoterViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoterViewHolder, position: Int) {
        val voter = voters[position]
        holder.bind(voter)
    }

    override fun getItemCount(): Int {
        return voters.size
    }

    inner class VoterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvNik: TextView = itemView.findViewById(R.id.tvNik)
        private val tvGender: TextView = itemView.findViewById(R.id.tvGender)
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val ivEdit: ImageView = itemView.findViewById(R.id.ivEdit)
        private val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
        private val ivEye: ImageView = itemView.findViewById(R.id.ivView) // Add this line for eye icon

        fun bind(voter: VoterEntity) {
            tvName.text = voter.name
            tvNik.text = voter.nik
            tvGender.text = voter.gender
            tvAddress.text = voter.address

            ivEdit.setOnClickListener {
                onItemClicked(voter)  // Aksi untuk mengedit
            }

            ivDelete.setOnClickListener {
                onDeleteClicked(voter)  // Aksi untuk menghapus
            }

            ivEye.setOnClickListener {
                onEyeClicked(voter)  // Aksi untuk melihat detail (ikon mata)
            }
        }
    }
}
