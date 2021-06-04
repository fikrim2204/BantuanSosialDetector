package app.capstone.bantuansosialdetector.submit

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.capstone.bantuansosialdetector.R
import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.ui.TrackingAdapter
import app.capstone.bantuansosialdetector.core.utils.Prefs
import app.capstone.bantuansosialdetector.databinding.FragmentResultBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val prefs: Prefs by inject()

    private val viewModel: ResultViewModel by viewModel()
    private val recipient: Recipient by inject()
    private val trackingAdapter: TrackingAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trackingAdapter.onItemClick = { selectedItem ->
            val action =
                ResultFragmentDirections.actionResultFragmentToTrackingFragment(selectedItem.id.toString())
            findNavController().navigate(action)
        }

        val userNik = prefs.nikUserPref
        if (userNik != null) {
            viewModel.inputNikGetRecipient(userNik)
            nikInserted()
        } else {
            emptyData()
        }

        with(binding.rvTracking) {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = trackingAdapter
        }
    }

    private fun emptyData() {
        with(binding) {
            lyEmpty.root.visibility = View.VISIBLE
            lyEmpty.btnAlreadySubmit.setOnClickListener {
                val mDialogAlreadySubmitFragment = DialogAlreadySubmitFragment()
                val mFragmentManager = childFragmentManager
                mDialogAlreadySubmitFragment.show(mFragmentManager, DialogAlreadySubmitFragment::class.java.simpleName)
            }
            fabAddSubmit.visibility = View.VISIBLE
            fabAddSubmit.setOnClickListener {
                findNavController().navigate(R.id.action_resultFragment_to_submitFragment)
            }
        }
    }

    private fun nikInserted() {
        viewModel.nikGetRecipient.observe(viewLifecycleOwner, { nik ->
            getRecipient(nik)
        })
    }

    private fun getRecipient(nik: String?) {
        viewModel.getRecipientByNik(nik).observe(viewLifecycleOwner, { responseRecipient ->
            when (responseRecipient) {
                is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    if (responseRecipient.data?.isNotEmpty() == true) {
                        with(binding) {
                            lyEmpty.root.visibility = View.GONE
                            fabAddSubmit.visibility = View.GONE
                        }

                        prefs.nikUserPref = responseRecipient.data[0].noNik.toString()
                        recipient.id = responseRecipient.data[0].id
                        recipient.gaji = responseRecipient.data[0].gaji
                        recipient.tanggungan = responseRecipient.data[0].tanggungan
                        recipient.pekerjaan = responseRecipient.data[0].pekerjaan
                        recipient.umur = responseRecipient.data[0].umur
                        recipient.status = responseRecipient.data[0].status

                        val listGaji = mutableListOf<Double?>()
                        listGaji.add(recipient.gaji)
                        val listPekerjaan = mutableListOf<Int?>()
                        listPekerjaan.add(recipient.pekerjaan)
                        val listTanggungan = mutableListOf<Int?>()
                        listTanggungan.add(recipient.tanggungan)
                        val listUmur = mutableListOf<Int?>()
                        listUmur.add(recipient.umur)

                        val predict = Predict(listGaji, listPekerjaan, listTanggungan, listUmur)

                        viewModel.inputPostPredict(predict)
                        inputPostPredict()
                    } else {
                        binding.progressBar.visibility = View.GONE
                        prefs.nikUserPref = null
                        Toast.makeText(
                            requireActivity(),
                            "Nik not registered ",
                            Toast.LENGTH_SHORT
                        ).show()
                        emptyData()
                    }
                }
                is Resource.Error -> {
                    Log.d("ResultFragment", responseRecipient.message.toString())
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireActivity(), responseRecipient.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun inputPostPredict() {
        viewModel.postPredict()
            .observe(viewLifecycleOwner, { resultPredict ->
                when (resultPredict) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        val result = resultPredict.data?.predictions?.get(0)?.get(0)
                        if (result != null) {
                            if (result >= 0.5) {
                                viewModel.updateRecipient(recipient.id, 1)
                                updateRecipient()
                            } else {
                                viewModel.updateRecipient(recipient.id, 0)
                                updateRecipient()
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.d("ResultFragment", resultPredict.message.toString())
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), resultPredict.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

    private fun updateRecipient() {
        viewModel.updateRecipientById().observe(viewLifecycleOwner, { resultUpdate ->
            when (resultUpdate) {
                is Resource.Loading -> binding.progressBar.visibility =
                    View.VISIBLE
                is Resource.Success -> {
                    if (resultUpdate.data?.status == 1) {
                        with(binding) {
                            tvResult.text = getString(R.string.congratulation)
                            tvResult.setTextColor(Color.parseColor("#4CAF50"))
                            tvResultDetail.text = getString(R.string.accepted_message)
                            tvStatusDelivery.text = getString(R.string.status_delivery)

                            viewModel.inputGetTracking(resultUpdate.data.nik)
                            getTracking()
                        }
                    } else {
                        with(binding) {
                            tvResult.text = getString(R.string.sorry)
                            tvResult.setTextColor(Color.parseColor("#F44336"))
                            tvResultDetail.text = getString(R.string.declined_message)
                            tvStatusDelivery.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireActivity(), resultUpdate.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun getTracking() {
        viewModel.getTracking().observe(
            viewLifecycleOwner,
            { resultTracking ->
                when (resultTracking) {
                    is Resource.Loading -> binding.progressBar.visibility =
                        View.VISIBLE
                    is Resource.Success -> {
                        with(binding) {
                            progressBar.visibility = View.GONE
                            rvTracking.visibility = View.VISIBLE
                            if (resultTracking.data?.isNotEmpty() == true) {
                                binding.tvMessageDelivery.visibility = View.GONE
                                trackingAdapter.setList(resultTracking.data)
                            } else {
                                binding.tvMessageDelivery.visibility = View.VISIBLE
                                binding.tvMessageDelivery.text = getText(R.string.delivery_waiting)
                            }
                        }
                    }
                    is Resource.Error -> {
                        with(binding) {
                            progressBar.visibility = View.GONE
                            rvTracking.visibility = View.GONE
                            Toast.makeText(
                                requireActivity(),
                                "RvTracking: ${resultTracking.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
    }

    internal var onDialogClickListener: DialogAlreadySubmitFragment.OnDialogClickListener =
        object : DialogAlreadySubmitFragment.OnDialogClickListener {
            override fun onButtonOkClicked(text: String?) {
                if (text != null) {
                    viewModel.inputNikGetRecipient(text)
                    nikInserted()
                } else {
                    emptyData()
                }
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profileFragment -> {
                findNavController().navigate(R.id.action_resultFragment_to_profileFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}