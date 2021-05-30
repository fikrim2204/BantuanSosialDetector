package app.capstone.bantuansosialdetector.submit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.capstone.bantuansosialdetector.R
import app.capstone.bantuansosialdetector.databinding.FragmentSubmitBinding

class SubmitFragment : Fragment() {
    private var _binding: FragmentSubmitBinding? = null
    private val binding get() = _binding!!

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
        binding.atvJob.setAdapter(adapter)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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