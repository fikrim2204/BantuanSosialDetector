package app.capstone.bansosadmin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.capstone.bansosadmin.databinding.ItemTrackBinding
import app.capstone.bansosadmin.domain.model.Tracks
import app.capstone.bansosadmin.utils.formatDate

class TrackingAdapter(private val listener: UserInteractionListener) :
    RecyclerView.Adapter<TrackingAdapter.ViewHolder>() {
    private var listTracks = ArrayList<Tracks>()

    fun setList(list: List<Tracks>?) {
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

    override fun onBindViewHolder(holder: TrackingAdapter.ViewHolder, position: Int) {
        with(listTracks[position]) {
            holder.bind(this)
        }
    }

    override fun getItemCount() = listTracks.size

    inner class ViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tracks: Tracks?) = with(binding) {
            tracks?.let { data ->
                tvAlamat.text = data.alamat
                tvStatus.text = data.status
                tvWaktu.text = formatDate(data.updated_at)
                btnDelete.setOnClickListener { listener.onUserItemClickListener(it, true, data) }
                cardItemTrack.setOnClickListener { listener.onUserItemClickListener(it, false, data) }
            }
        }
    }

    interface UserInteractionListener {
        fun onUserItemClickListener(view: View, state: Boolean, tracks: Tracks?)
    }
}