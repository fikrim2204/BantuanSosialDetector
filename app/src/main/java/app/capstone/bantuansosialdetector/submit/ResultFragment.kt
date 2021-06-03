package app.capstone.bantuansosialdetector.submit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.capstone.bantuansosialdetector.R
import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.domain.model.Predict
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
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

        val userNik = prefs.nikUserPref
        if (userNik != null) {
            viewModel.getRecipientByNik(userNik).observe(viewLifecycleOwner, { responseRecipient ->
                when (responseRecipient) {
                    is Resource.Loading -> {
                        with(binding) {
                            progressBar.visibility = View.VISIBLE
                            fabAddSubmit.visibility = View.GONE
                        }
                    }
                    is Resource.Success -> {
                        with(binding) {
                            progressBar.visibility = View.GONE
                            recipient.id = responseRecipient.data?.get(0)?.id
                            recipient.gaji = responseRecipient.data?.get(0)?.gaji
                            recipient.tanggungan = responseRecipient.data?.get(0)?.tanggungan
                            recipient.pekerjaan = responseRecipient.data?.get(0)?.pekerjaan
                            recipient.umur = responseRecipient.data?.get(0)?.umur
                            recipient.status = responseRecipient.data?.get(0)?.status

                            val listGaji = mutableListOf<Double?>()
                            listGaji.add(recipient.gaji)
                            val listPekerjaan = mutableListOf<Int?>()
                            listPekerjaan.add(recipient.pekerjaan)
                            val listTanggungan = mutableListOf<Int?>()
                            listTanggungan.add(recipient.tanggungan)
                            val listUmur = mutableListOf<Int?>()
                            listUmur.add(recipient.umur)

                            val predict =
                                Predict(listGaji, listPekerjaan, listTanggungan, listUmur)
                            viewModel.postPredict(predict)
                                .observe(viewLifecycleOwner, { resultPredict ->
                                    when (resultPredict) {
                                        is Resource.Loading -> binding.progressBar.visibility =
                                            View.VISIBLE
                                        is Resource.Success -> {
                                            binding.progressBar.visibility = View.GONE
                                            val result =
                                                resultPredict.data?.predictions?.get(0)?.get(0)
                                            if (result != null) {
                                                if (result >= 0.5)
                                                    viewModel.updateRecipientById(
                                                        recipient.id,
                                                        1
                                                    ).observe(viewLifecycleOwner, { resultUpdate ->
                                                        when (resultUpdate) {
                                                            is Resource.Loading -> binding.progressBar.visibility =
                                                                View.VISIBLE
                                                            is Resource.Success -> {
                                                                if (resultUpdate.data?.status == 1) {
                                                                    binding.progressBar.visibility =
                                                                        View.GONE
                                                                    tvResult.text =
                                                                        getString(R.string.congratulation)
                                                                    tvResultDetail.text =
                                                                        getString(R.string.accepted_message)
                                                                    tvStatusDelivery.text =
                                                                        getString(R.string.status_delivery)
                                                                } else {
                                                                    binding.progressBar.visibility =
                                                                        View.GONE
                                                                    tvResult.text =
                                                                        getString(R.string.sorry)
                                                                    tvResultDetail.text =
                                                                        getString(R.string.declined_message)
                                                                    tvStatusDelivery.visibility =
                                                                        View.GONE
                                                                }
                                                            }
                                                            is Resource.Error -> {
                                                                binding.progressBar.visibility =
                                                                    View.GONE
                                                                Toast.makeText(
                                                                    requireActivity(),
                                                                    resultPredict.message,
                                                                    Toast.LENGTH_SHORT
                                                                )
                                                                    .show()
                                                            }
                                                        }
                                                    })
                                            }
                                        }
                                        is Resource.Error -> {
                                            Log.d(
                                                "ResultFragment",
                                                resultPredict.message.toString()
                                            )
                                            binding.progressBar.visibility = View.GONE
                                            Toast.makeText(
                                                requireActivity(),
                                                resultPredict.message,
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                    }
                                })
                        }
                    }
                    is Resource.Error -> {
                        Log.d("ResultFragment", responseRecipient.message.toString())
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireActivity(),
                            responseRecipient.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
        } else if (recipient.gaji != null) {
            val listGaji = mutableListOf<Double?>()
            listGaji.add(recipient.gaji)
            val listPekerjaan = mutableListOf<Int?>()
            listPekerjaan.add(recipient.pekerjaan)
            val listTanggungan = mutableListOf<Int?>()
            listTanggungan.add(recipient.tanggungan)
            val listUmur = mutableListOf<Int?>()
            listUmur.add(recipient.umur)

            val predict =
                Predict(listGaji, listPekerjaan, listTanggungan, listUmur)
            viewModel.postPredict(predict).observe(viewLifecycleOwner, { resultPredict ->
                val result = resultPredict.data
                binding.tvResult.text = result.toString()
            })
        } else {
            with(binding) {
                lyEmpty.root.visibility = View.VISIBLE
                fabAddSubmit.visibility = View.VISIBLE
                fabAddSubmit.setOnClickListener {
                    findNavController().navigate(R.id.action_resultFragment_to_submitFragment)
                }
            }
        }

//        {
//            if (args.nikRecipient != null) {
//                val nik = args.nikRecipient
//                prefs.nikUserPref = nik
//                if (nik != null) {
//                    viewModel.getRecipientByNik(nik).observe(viewLifecycleOwner, { responseRecipient ->
//                        when (responseRecipient) {
//                            is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
//                            is Resource.Success -> {
//                                with(binding) {
//                                    fabAddSubmit.visibility = View.GONE
////                                    tvResult.text = responseRecipient.data?.get(0)?.noNik.toString()
////                                    recipient.gaji = responseRecipient.data?.gaji
////                                    recipient.pekerjaan = responseRecipient.data?.pekerjaan
////                                    recipient.tanggungan = responseRecipient.data?.tanggungan
////                                    recipient.umur = responseRecipient.data?.umur
//                                }
//                            }
//                            is Resource.Error -> {
//                                Log.d("ResultFragment", responseRecipient.message.toString())
//                                binding.progressBar.visibility = View.GONE
//                                Toast.makeText(
//                                    requireActivity(),
//                                    responseRecipient.message,
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    })
//
////                    val predict = Predict(recipient.gaji, recipient.pekerjaan, recipient.tanggungan, recipient.umur)
////                    viewModel.postPredict(predict)
//                }
//            } else {
//
//            }
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profileFragment -> {
                findNavController().navigate(R.id.action_resultFragment_to_profileFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}