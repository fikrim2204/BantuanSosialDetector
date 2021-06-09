package app.capstone.bansosadmin.ui.fragments.home

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import app.capstone.bansosadmin.data.Resource
import app.capstone.bansosadmin.databinding.FragmentHomeBinding
import app.capstone.bansosadmin.domain.model.Recipients
import app.capstone.bansosadmin.ui.adapters.RecipientAdapter
import app.capstone.bansosadmin.ui.fragments.BaseFragment
import app.capstone.bansosadmin.utils.hide
import app.capstone.bansosadmin.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(), RecipientAdapter.UserInteractionListener {
    private var recipientAdapter: RecipientAdapter? = null
    private val homeViewModel: HomeViewModel by viewModel()
    override fun getBindings(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipientAdapter = RecipientAdapter(this)
        with(binding) {
            with(rvList) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = recipientAdapter
                setHasFixedSize(true)
            }
            btnSearch.setOnClickListener {
                if (checkEmpty(edNik.text.toString().trim())) {
                    getRecipient(edNik.text.toString().trim())
                }
            }

        }
    }

    private fun getRecipient(nik: String) {
        homeViewModel.getPenerimaById(nik)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            if (it.isNotEmpty()) {
                                binding.progressBar.hide()
                                recipientAdapter?.setList(it)
                            } else {
                                binding.progressBar.hide()
                            }
                        }

                    }
                    is Resource.Error -> {
                        binding.progressBar.hide()
                        Toast.makeText(
                            requireContext(),
                            "Something Error! ${response.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBar.show()
                    }
                }
            }
    }

    private fun checkEmpty(nik: String): Boolean {
        if (TextUtils.isEmpty(nik)) {
            binding.edNik.error = "Required"
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recipientAdapter = null
    }

    override fun onUserItemClickListener(view: View, recipients: Recipients?) {
        val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(recipients)
        if (recipients?.status == 0) {
            Toast.makeText(
                requireContext(),
                "This recipient is not deserve for this yet!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Navigation.findNavController(view).navigate(action)
        }
    }
}