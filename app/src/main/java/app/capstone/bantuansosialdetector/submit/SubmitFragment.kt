package app.capstone.bantuansosialdetector.submit

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.capstone.bantuansosialdetector.R
import app.capstone.bantuansosialdetector.core.data.source.Resource
import app.capstone.bantuansosialdetector.core.domain.model.Recipient
import app.capstone.bantuansosialdetector.core.utils.Prefs
import app.capstone.bantuansosialdetector.databinding.FragmentSubmitBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubmitFragment : Fragment() {
    private var _binding: FragmentSubmitBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubmitViewModel by viewModel()
    private val prefs: Prefs by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentSubmitBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jobList = listOf(
            "Tidak Bekerja",
            "Pensiunan",
            "PNS",
            "Wiraswasta",
            "Karyawan",
            "Petani/Nelayan/Peternak",
            "Pegawai Pemerintah",
            "Pekerja Kasar",
            "Buruh Pabrik",
            "Pemuka Agama",
            "Lainnya"
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, jobList)

        with(binding) {
            atvJob.setAdapter(adapter)
            var job: Int? = null
            atvJob.setOnItemClickListener { _, _, i, _ ->
                job = i
                Log.d("Position job", i.toString())
            }
            btnSubmit.setOnClickListener {
                val noNik = etNoNik.text.toString().toLongOrNull()
                val name = etName.text.toString()
                val noHp = etNoTel.text.toString().toLongOrNull()
                val age = etAge.text.toString().toIntOrNull()
                val income = etIncome.text.toString().toIntOrNull()
                val dependents = etDependents.text.toString().toIntOrNull()
                val address = etAddress.text.toString()
                if (notEmpty(noNik, name, noHp, age, income, dependents, address, job)) {
                    val recipient =
                        Recipient(null, noNik, name, noHp, address, income, job, dependents, age, 0)
                    viewModel.insertRecipient(recipient)
                        .observe(viewLifecycleOwner) { recipientResult ->
                            when (recipientResult) {
                                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                                is Resource.Success -> {
                                    prefs.nikUserPref = recipientResult.data?.nik
                                    findNavController().navigateUp()
                                    Toast.makeText(
                                        requireActivity(),
                                        "Form sent",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                                is Resource.Error -> {
                                    Log.d("TAG", recipientResult.message.toString())
                                    findNavController().navigateUp()
                                    Toast.makeText(
                                        requireActivity(),
                                        recipientResult.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                }
            }
        }
    }

    private fun notEmpty(
        noNik: Long?,
        name: String,
        noHp: Long?,
        age: Int?,
        income: Int?,
        dependents: Int?,
        address: String,
        job: Int?
    ): Boolean {
        if (TextUtils.isEmpty(noNik.toString())) {
            binding.etNoNik.error = "Field must not be empty"
            return false
        }
        if (TextUtils.isEmpty(name)) {
            binding.etName.error = "Field must not be empty"
            return false
        }
        if (TextUtils.isEmpty(noHp.toString())) {
            binding.etNoTel.error = "Field must not be empty"
            return false
        }
        if (TextUtils.isEmpty(age.toString())) {
            binding.etAge.error = "Field must not be empty"
            return false
        }
        if (TextUtils.isEmpty(income.toString())) {
            binding.etIncome.error = "Field must not be empty"
            return false
        }
        if (TextUtils.isEmpty(dependents.toString())) {
            binding.etDependents.error = "Field must not be empty"
            return false
        }
        if (TextUtils.isEmpty(address)) {
            binding.etAddress.error = "Field must not be empty"
            return false
        }
        if (job == null) {
            binding.atvJob.error = "Select Job"
            return false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profileFragment -> {
                findNavController().navigate(R.id.action_submitFragment_to_profileFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}