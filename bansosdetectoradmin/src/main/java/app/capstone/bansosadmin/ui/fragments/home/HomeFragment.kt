package app.capstone.bansosadmin.ui.fragments.home

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import app.capstone.bansosadmin.data.Resource
import app.capstone.bansosadmin.databinding.FragmentHomeBinding
import app.capstone.bansosadmin.domain.model.Penerima
import app.capstone.bansosadmin.ui.adapters.PenerimaAdapter
import app.capstone.bansosadmin.ui.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(), PenerimaAdapter.UserInteractionListener {
    private var penerimaAdapter: PenerimaAdapter? = null
    private val homeViewModel: HomeViewModel by viewModel()
    override fun getBindings(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        penerimaAdapter = PenerimaAdapter(this)
        with(binding) {
            with(rvList) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = penerimaAdapter
                setHasFixedSize(true)
            }
            with(btnSearch) {
                setOnClickListener {
                    if (checkEmpty(edNik.text.toString().trim())) {
                        Log.i("TAGG", "onViewCreated: ${edNik.text} ")
                        homeViewModel.getPenerimaById(edNik.text.toString()).observe(viewLifecycleOwner, {response ->
                            when (response) {
                                is Resource.Success -> {
                                    response.data?.let {
                                        if (it.isNotEmpty()) {
                                            binding.progressBar.hide()
//                                            binding.rvMovie.show()
//                                            binding.notFoundLayout.hide()
                                            penerimaAdapter?.setList(it)
                                        }else {
//                                            binding.notFoundLayout.show()
//                                            binding.rvMovie.hide()
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
                        })
                    }
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

    override fun onUserItemClickListener(penerima: Penerima?) {

    }
}