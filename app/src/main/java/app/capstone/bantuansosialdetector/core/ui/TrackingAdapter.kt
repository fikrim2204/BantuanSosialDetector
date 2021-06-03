package app.capstone.bantuansosialdetector.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.capstone.bantuansosialdetector.core.domain.model.Tracking
import app.capstone.bantuansosialdetector.databinding.ItemLayoutTrackingBinding

class TrackingAdapter : RecyclerView.Adapter<TrackingAdapter.ViewHolder>() {
    private var listTracking = ArrayList<Tracking>()
    var onItemClick: ((Tracking) -> Unit)? = null

    fun setList(list: List<Tracking>?) {
        if (list == null) return
        listTracking.clear()
        listTracking.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemLayoutTrackingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tracking: Tracking) = with(binding) {
            tracking.let { data ->
                tvAddress.text = data.alamat
                tvStatus.text = data.status
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listTracking[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemLayoutTrackingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(listTracking[position]) {
            holder.bind(this)
        }
    }

    override fun getItemCount() = listTracking.size
}