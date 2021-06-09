package app.capstone.bansosadmin.ui.fragments.insert

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import app.capstone.bansosadmin.data.Resource
import app.capstone.bansosadmin.databinding.FragmentInsertTrackBinding
import app.capstone.bansosadmin.domain.model.Insert
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class InsertTrackFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentInsertTrackBinding? = null
    private val binding get() = _binding!!
    private val insertViewModel: InsertViewModel by viewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        _binding = FragmentInsertTrackBinding.inflate(layoutInflater)
        with(binding) {
            val params = rootInsert.layoutParams as LinearLayout.LayoutParams
            params.height = Resources.getSystem().displayMetrics.heightPixels
            dialog.setContentView(this.root)
        }
        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = FragmentInsertTrackBinding.inflate(inflater, container, false)
        val dialog: AlertDialog
        val alert = AlertDialog.Builder(requireContext())
        dialog = alert.setCancelable(false).create()
        arguments?.let {
            val recipient = InsertTrackFragmentArgs.fromBundle(it).detail
            with(rootView) {
                edtNik.setText(recipient?.nik.toString())
                edtAddress.setText(recipient?.alamat)
                btnSubmit.setOnClickListener {
                    if (checkEmpty(edtNik.text.toString(), edtAddress.text.toString())) {
                        val insert = Insert(edtNik.text.toString(),null ,edtAddress.text.toString())
                        insertViewModel.insert(insert).observe(viewLifecycleOwner, { response ->
                            when (response) {
                                is Resource.Error -> {
                                    Toast.makeText(requireContext(), "Fail to add tracks data!", Toast.LENGTH_SHORT).show()
                                    dismiss()
                                }
                                is Resource.Loading -> {
                                    dialog.show()
                                }
                                is Resource.Success -> {
                                    dialog.dismiss()
                                    Toast.makeText(requireContext(), "Tracks data successfully added!", Toast.LENGTH_SHORT).show()
                                    dismiss()
                                }
                            }
                        })
                    }
                }
            }
        }
        return rootView.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkEmpty(nik: String, address: String): Boolean {
        if (TextUtils.isEmpty(nik)) {
            binding.edtNik.error = "Required"
            return false
        }
        if (TextUtils.isEmpty(address)) {
            binding.edtAddress.error = "Required"
            return false
        }
        return true
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        findNavController().previousBackStackEntry?.savedStateHandle?.set("dismiss", true)
    }
}