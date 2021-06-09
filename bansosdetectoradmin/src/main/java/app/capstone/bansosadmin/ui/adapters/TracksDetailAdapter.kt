package app.capstone.bansosadmin.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.capstone.bansosadmin.databinding.ItemTrackBinding
import app.capstone.bansosadmin.domain.model.DetailTracks
import app.capstone.bansosadmin.utils.formatDate
import app.capstone.bansosadmin.utils.hide

class TracksDetailAdapter : RecyclerView.Adapter<TracksDetailAdapter.ViewHolder>() {
    private var listTracks = ArrayList<DetailTracks>()

    fun setList(list: List<DetailTracks>?) {
        if (list == null) return
        listTracks.clear()
        listTracks.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TracksDetailAdapter.ViewHolder, position: Int) {
        with(listTracks[position]) {
            holder.bind(this)
        }
    }

    override fun getItemCount() = listTracks.size
    inner class ViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(detailTracks: DetailTracks?) = with(binding) {
            detailTracks?.let { data ->
                tvStatus.hide()
                btnDelete.hide()
                tvTips.hide()
                tvAlamat.text = data.lokasi
                tvWaktu.text = formatDate(data.waktu)
            }
        }
    }
}