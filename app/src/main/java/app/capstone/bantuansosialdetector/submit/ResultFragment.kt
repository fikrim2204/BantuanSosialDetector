package app.capstone.bantuansosialdetector.submit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.capstone.bantuansosialdetector.R
import app.capstone.bantuansosialdetector.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = null
        if (data == null) {
            with(binding) {
                fabAddSubmit.visibility = View.VISIBLE
                fabAddSubmit.setOnClickListener {
                    findNavController().navigate(R.id.action_resultFragment_to_submitFragment)
                }
                lyEmpty.root.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                fabAddSubmit.visibility = View.GONE
            }
        }
    }
}