package app.capstone.bantuansosialdetector.submit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.capstone.bantuansosialdetector.R
import app.capstone.bantuansosialdetector.core.utils.Prefs
import app.capstone.bantuansosialdetector.databinding.FragmentResultBinding
import org.koin.android.ext.android.inject

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val args: ResultFragmentArgs by navArgs()
    private val prefs: Prefs by inject()

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

        val userId = prefs.idUserPref
        if (userId != null) {
            //GetData
            with(binding) {
                fabAddSubmit.visibility = View.GONE
                tvResult.text = userId
            }
        } else {
            if (args.idRecipient != null) {
                val id = args.idRecipient
                prefs.idUserPref = id
                if (id != null) {
                    with(binding) {
                        fabAddSubmit.visibility = View.GONE
                        tvResult.text = id
                    }
                }
            } else {
                binding.lyEmpty.root.visibility = View.VISIBLE
                with(binding) {
                    fabAddSubmit.visibility = View.VISIBLE
                    fabAddSubmit.setOnClickListener {
                        findNavController().navigate(R.id.action_resultFragment_to_submitFragment)
                    }
                    lyEmpty.root.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.profileFragment -> {
                findNavController().navigate(R.id.action_resultFragment_to_profileFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}