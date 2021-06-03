package app.capstone.bantuansosialdetector.submit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import app.capstone.bantuansosialdetector.core.utils.Prefs
import app.capstone.bantuansosialdetector.databinding.FragmentDialogAlreadySubmitBinding
import org.koin.android.ext.android.inject

class DialogAlreadySubmitFragment : DialogFragment() {
    private var _binding: FragmentDialogAlreadySubmitBinding? = null
    private val binding get() = _binding!!

    private val prefs: Prefs by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogAlreadySubmitBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnOk.setOnClickListener {
            val noNik = binding.etNoNik.text.toString()
            prefs.nikUserPref = noNik
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }

    companion object {
        const val TAG = "DialogAlreadySubmitFragment"
    }
}