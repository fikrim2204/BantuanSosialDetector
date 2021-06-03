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
import androidx.navigation.fragment.navArgs
import app.capstone.bantuansosialdetector.R
import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.utils.Prefs
import app.capstone.bantuansosialdetector.databinding.FragmentResultBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val args: ResultFragmentArgs by navArgs()
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
            viewModel.getRecipientByNik(userNik).observe(viewLifecycleOwner, { recipient ->
                when (recipient) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        with(binding) {
                            progressBar.visibility = View.GONE
                            fabAddSubmit.visibility = View.GONE
                            tvResult.text = recipient.data?.get(0)?.noNik.toString()
                        }
                    }
                    is Resource.Error -> {
                        Log.d("ResultFragment", recipient.message.toString())
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), recipient.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        } else {
            if (args.nikRecipient != null) {
                val nik = args.nikRecipient
                prefs.nikUserPref = nik
                if (nik != null) {
                    viewModel.getRecipientByNik(nik).observe(viewLifecycleOwner, { responseRecipient ->
                        when (responseRecipient) {
                            is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                            is Resource.Success -> {
                                with(binding) {
                                    fabAddSubmit.visibility = View.GONE
//                                    tvResult.text = responseRecipient.data?.get(0)?.noNik.toString()
//                                    recipient.gaji = responseRecipient.data?.gaji
//                                    recipient.pekerjaan = responseRecipient.data?.pekerjaan
//                                    recipient.tanggungan = responseRecipient.data?.tanggungan
//                                    recipient.umur = responseRecipient.data?.umur
                                }
                            }
                            is Resource.Error -> {
                                Log.d("ResultFragment", responseRecipient.message.toString())
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    requireActivity(),
                                    responseRecipient.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })

//                    val predict = Predict(recipient.gaji, recipient.pekerjaan, recipient.tanggungan, recipient.umur)
//                    viewModel.postPredict(predict)
                }
            } else {
                with(binding) {
                    lyEmpty.root.visibility = View.VISIBLE
                    fabAddSubmit.visibility = View.VISIBLE
                    fabAddSubmit.setOnClickListener {
                        findNavController().navigate(R.id.action_resultFragment_to_submitFragment)
                    }
                }
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
}