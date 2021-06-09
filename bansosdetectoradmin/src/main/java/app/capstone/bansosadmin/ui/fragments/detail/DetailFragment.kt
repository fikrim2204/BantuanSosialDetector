package app.capstone.bansosadmin.ui.fragments.detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.capstone.bansosadmin.R
import app.capstone.bansosadmin.data.Resource
import app.capstone.bansosadmin.databinding.FragmentDetailBinding
import app.capstone.bansosadmin.domain.model.Recipients
import app.capstone.bansosadmin.domain.model.Tracks
import app.capstone.bansosadmin.ui.adapters.TrackingAdapter
import app.capstone.bansosadmin.ui.fragments.BaseFragment
import app.capstone.bansosadmin.utils.hide
import app.capstone.bansosadmin.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(),
    TrackingAdapter.UserInteractionListener {
    private var trackingAdapter: TrackingAdapter? = null
    private val detailTrackViewModel: DetailTrackViewModel by viewModel()
    private var detailUser: Recipients? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        trackingAdapter = TrackingAdapter(this)
        arguments?.let {
            detailUser = DetailFragmentArgs.fromBundle(it).detail
            with(binding) {
                edNama.setText(detailUser?.nama)
                edAlamat.setText(detailUser?.alamat)
                edNoHp.setText(detailUser?.no_hp.toString())
                with(rvTrack) {
                    layoutManager = LinearLayoutManager(requireContext())
                    setHasFixedSize(true)
                    adapter = trackingAdapter
                }
                detailTrackViewModel.getTracking(detailUser?.nik.toString())
            }
        }
        observableTrack()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> {
                val action =
                    DetailFragmentDirections.actionDetailFragmentToInsertTrackFragment(detailUser)
                Navigation.findNavController(requireView()).navigate(action)
            }
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observableTrack() {
        detailTrackViewModel.tracking.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressBar.hide()
                    response.data.let {
                        if (!it.isNullOrEmpty()) {
                            trackingAdapter?.setList(it)
                            binding.tvNoTrack.hide()
                            binding.rvTrack.show()
                        } else {
                            binding.tvNoTrack.show()
                            binding.rvTrack.hide()
                        }
                    }
                }
                is Resource.Loading -> binding.progressBar.show()
                is Resource.Error -> Toast.makeText(
                    requireContext(),
                    "Error when getting recipient tracks!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            "dismiss"
        )?.observe(viewLifecycleOwner, {
            it?.let {
                detailTrackViewModel.getTracking(detailUser?.nik.toString())
            }
        })
    }

    override fun getBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailBinding.inflate(inflater, container, false)

    override fun onUserItemClickListener(view: View, state: Boolean, tracks: Tracks?) {
        if (state) {
            val dialog = AlertDialog.Builder(requireContext())
                .setIcon(android.R.drawable.ic_delete)
                .setCancelable(false)
                .setTitle(getString(R.string.confirm_delete))
                .setMessage(getString(R.string.confirm_message))
                .setPositiveButton(
                    getString(R.string.yes)
                ) { dialog, _ ->
                    dialog.dismiss()
                    deleteTrack(tracks?.id.toString())
                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
            dialog.show()
        } else {
            if (tracks?.status == "Selesai") {
                Toast.makeText(
                    requireContext(),
                    "Can't update tracks, because already finished",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val action =
                    DetailFragmentDirections.actionDetailFragmentToUpdateTrackFragment(
                        tracks?.id.toString(),
                        tracks?.nik_penerima.toString()
                    )
                Navigation.findNavController(view).navigate(action)
            }
        }

    }

    private fun deleteTrack(id: String) {
        detailTrackViewModel.deleteTrackById(id).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Error -> Toast.makeText(
                    requireContext(),
                    "Error when trying to delete Track!",
                    Toast.LENGTH_SHORT
                )
                    .show()
                is Resource.Loading -> {
                    dialog?.show()
                }
                is Resource.Success -> {
                    dialog?.dismiss()
                    Toast.makeText(requireContext(), "Track deleted!", Toast.LENGTH_SHORT).show()
                    detailTrackViewModel.getTracking(detailUser?.nik.toString())
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        trackingAdapter = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }
}