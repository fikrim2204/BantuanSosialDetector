package app.capstone.bantuansosialdetector.tracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.ui.DetailTrackingAdapter
import app.capstone.bantuansosialdetector.databinding.FragmentTrackingBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrackingFragment : Fragment() {
    private var _binding : FragmentTrackingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrackingViewModel by viewModel()
    private val args: TrackingFragmentArgs by navArgs()
    private val detailTrackingAdapter: DetailTrackingAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val idTracking = args.idTracking
            if (idTracking != null) {
                with(binding) {
                    viewModel.getDetailTracking(idTracking).observe(viewLifecycleOwner, { detailTrack ->
                        when (detailTrack) {
                            is Resource.Loading -> progressBar.visibility = View.VISIBLE
                            is Resource.Success -> {
                                binding.progressBar.visibility = View.GONE
                                rvTracking.visibility = View.VISIBLE
                                detailTrackingAdapter.setList(detailTrack.data)
                            }
                            is Resource.Error -> {
                                binding.progressBar.visibility = View.GONE
                                rvTracking.visibility = View.VISIBLE
                                Toast.makeText(requireActivity(), "${detailTrack.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                    rvTracking.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                    rvTracking.setHasFixedSize(true)
                    rvTracking.adapter = detailTrackingAdapter
                }
            }
        }
    }
}