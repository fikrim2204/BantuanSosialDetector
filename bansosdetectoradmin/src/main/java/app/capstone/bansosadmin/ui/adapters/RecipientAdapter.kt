package app.capstone.bansosadmin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.capstone.bansosadmin.databinding.ItemLayoutBinding
import app.capstone.bansosadmin.domain.model.Recipients

class RecipientAdapter(private val listener: UserInteractionListener) :
    RecyclerView.Adapter<RecipientAdapter.ViewHolder>() {
    private var recipientList = ArrayList<Recipients>()

    fun setList(list: List<Recipients>?) {
        if (list == null) return
        recipientList.clear()
        recipientList.addAll(list)
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipients: Recipients?) = with(binding) {
            recipients?.let { data ->
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
        with(recipientList[position]) {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                listener.onUserItemClickListener(it,this)
            }
        }
    }

    override fun getItemCount() = recipientList.size
    interface UserInteractionListener {
        fun onUserItemClickListener(view: View, recipients: Recipients?)
    }
}