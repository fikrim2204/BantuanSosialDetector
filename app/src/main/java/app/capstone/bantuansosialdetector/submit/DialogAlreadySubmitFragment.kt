package app.capstone.bantuansosialdetector.submit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import app.capstone.bantuansosialdetector.databinding.FragmentDialogAlreadySubmitBinding

class DialogAlreadySubmitFragment : DialogFragment() {
    private var _binding: FragmentDialogAlreadySubmitBinding? = null
    private val binding get() = _binding!!
    private var dialogClickListener: OnDialogClickListener? = null

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
            dialogClickListener?.onButtonOkClicked(noNik)
//            prefs.nikUserPref = noNik
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment

        if (fragment is ResultFragment) {
            this.dialogClickListener = fragment.onDialogClickListener
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    interface OnDialogClickListener {
        fun onButtonOkClicked(text: String?)
    }

    override fun onDetach() {
        super.onDetach()
        this.dialogClickListener = null
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }

    companion object {
        const val TAG = "DialogAlreadySubmitFragment"
    }
}