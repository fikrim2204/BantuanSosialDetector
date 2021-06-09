package app.capstone.bansosadmin.ui.fragments.update

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import app.capstone.bansosadmin.R
import app.capstone.bansosadmin.data.Resource
import app.capstone.bansosadmin.databinding.FragmentUpdateTrackBinding
import app.capstone.bansosadmin.domain.model.Insert
import app.capstone.bansosadmin.domain.model.UpdateItemTrack
import app.capstone.bansosadmin.ui.adapters.TracksDetailAdapter
import app.capstone.bansosadmin.ui.fragments.BaseFragment
import app.capstone.bansosadmin.ui.fragments.detail.DetailFragmentDirections
import app.capstone.bansosadmin.utils.getCurrentDate
import app.capstone.bansosadmin.utils.hide
import app.capstone.bansosadmin.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateTrackFragment : BaseFragment<FragmentUpdateTrackBinding>() {
    private lateinit var detailAdapter: TracksDetailAdapter
    private val updateTrackViewModel: UpdateTrackViewModel by viewModel()
    override fun getBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUpdateTrackBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailAdapter = TracksDetailAdapter()
        setHasOptionsMenu(true)
        arguments?.let {
            val id = UpdateTrackFragmentArgs.fromBundle(it).id!!
            val nik = UpdateTrackFragmentArgs.fromBundle(it).nik!!
            with(binding) {
                with(rvTrack) {
                    adapter = detailAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    setHasFixedSize(true)
                }
                btnUpdate.setOnClickListener {
                    if (checkEmpty(edLokasi.text.toString().trim())) updateTracks(
                        id,
                        edLokasi.text.toString()
                    )
                }
                btnFinish.setOnClickListener {
                    val dialog = AlertDialog.Builder(requireContext())
                        .setIcon(android.R.drawable.ic_delete)
                        .setCancelable(false)
                        .setTitle(getString(R.string.confirm_finish))
                        .setMessage(getString(R.string.confirm_message_finish))
                        .setPositiveButton(
                            getString(R.string.yes)
                        ) { dialog, _ ->
                            dialog.dismiss()
                            val insert = Insert(nik, id.toInt())
                            finishTrack(insert)
                        }
                        .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                            dialog.dismiss()
                        }
                    dialog.show()

                }
            }
            getTrackDetail(id)
        }
    }

    private fun finishTrack(insert: Insert) {
        updateTrackViewModel.finishTrack(insert).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error when trying to finish the tracks!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is Resource.Loading -> dialog?.show()
                is Resource.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Finishing track successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog?.dismiss()
                    activity?.onBackPressed()
                }
            }

        })
    }

    private fun updateTracks(id: String, location: String) {
        val updateTrack = UpdateItemTrack(location, getCurrentDate())
        updateTrackViewModel.update(id, updateTrack).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Error -> Toast.makeText(
                    requireContext(),
                    "Failure when trying to update Tracks! ${response.message}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                is Resource.Loading -> dialog?.show()
                is Resource.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Tracks update successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog?.dismiss()
                    getTrackDetail(id)
                }
            }
        })
    }

    private fun getTrackDetail(id: String) {
        updateTrackViewModel.tracksDetail(id).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error when trying to get Tracks Detail!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> binding.progressBar.show()
                is Resource.Success -> {
                    if (!response.data.isNullOrEmpty()) {
                        detailAdapter.setList(response.data)
                        binding.progressBar.hide()
                        binding.rvTrack.show()
                        binding.tvNoTrack.hide()
                    } else {
                        binding.progressBar.hide()
                        binding.rvTrack.hide()
                        binding.tvNoTrack.show()
                    }

                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkEmpty(id: String): Boolean {
        if (TextUtils.isEmpty(id)) {
            binding.edLokasi.error = "Required"
            return false
        }
        return true
    }
}