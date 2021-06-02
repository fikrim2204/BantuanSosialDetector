package app.capstone.bansosadmin.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.capstone.bansosadmin.databinding.ItemLayoutBinding
import app.capstone.bansosadmin.domain.model.Penerima

class PenerimaAdapter(private val listener: UserInteractionListener) : RecyclerView.Adapter<PenerimaAdapter.ViewHolder>() {
    private var listPenerima = ArrayList<Penerima>()

    fun setList(list: List<Penerima>?) {
        if (list == null) return
        listPenerima.clear()
        listPenerima.addAll(list)
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(penerima: Penerima?) = with(binding) {
            penerima?.let { data ->
                tvNik.text = data.nik.toString()
                tvNama.text = data.nama.toString()
                tvAlamat.text = data.alamat
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(listPenerima[position]) {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                listener.onUserItemClickListener(this)
            }
        }
    }

    override fun getItemCount() = listPenerima.size
    interface UserInteractionListener {
        fun onUserItemClickListener(penerima: Penerima?)
    }
}