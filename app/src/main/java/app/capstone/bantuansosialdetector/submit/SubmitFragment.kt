package app.capstone.bantuansosialdetector.submit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import app.capstone.bantuansosialdetector.databinding.FragmentSubmitBinding

class SubmitFragment : Fragment() {
    private var _binding: FragmentSubmitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}