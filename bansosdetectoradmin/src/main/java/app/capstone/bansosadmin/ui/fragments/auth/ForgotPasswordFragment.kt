package app.capstone.bansosadmin.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import app.capstone.bansosadmin.databinding.FragmentForgotPasswordBinding
import app.capstone.bansosadmin.ui.fragments.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {
    private val auth: FirebaseAuth by inject()
    override fun getBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentForgotPasswordBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            val email = binding.etEmail.text.toString()
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireActivity(),
                            "Reset link sent to your email",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Unable to send reset mail",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}