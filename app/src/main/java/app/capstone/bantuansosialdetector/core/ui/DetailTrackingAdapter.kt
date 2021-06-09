package app.capstone.bantuansosialdetector.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.capstone.bantuansosialdetector.core.domain.model.DetailTracking
import app.capstone.bantuansosialdetector.databinding.ItemLayoutDetailTrackingBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailTrackingAdapter : RecyclerView.Adapter<DetailTrackingAdapter.ViewHolder>() {
    private var listDetailTracking = ArrayList<DetailTracking>()
    var onItemClick: ((DetailTracking) -> Unit)? = null

    fun setList(list: List<DetailTracking>?) {
        if (list == null) return
        listDetailTracking.clear()
        listDetailTracking.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemLayoutDetailTrackingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(detailTracking: DetailTracking) = with(binding) {
            detailTracking.let { data ->
                try {
                    if (data.waktu != null){
                        val dateAndTimePattern = "yyyy-MM-dd hh:mm:ss"
                        val format = SimpleDateFormat(dateAndTimePattern, Locale.getDefault())
                        val d = format.parse(data.waktu)
                        val time = SimpleDateFormat("hh:mm", Locale.getDefault())
                        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                        tvDate.text = date.format(d)
                        tvTime.text = time.format(d)
                    }
                } catch (e: ParseException) {

                }
                tvMessage.text = data.lokasi
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listDetailTracking[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemLayoutDetailTrackingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(listDetailTracking[position]) {
            holder.bind(this)
        }
    }

    override fun getItemCount() = listDetailTracking.size
}