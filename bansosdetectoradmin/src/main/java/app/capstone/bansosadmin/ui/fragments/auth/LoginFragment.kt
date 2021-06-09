package app.capstone.bansosadmin.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import app.capstone.bansosadmin.R
import app.capstone.bansosadmin.databinding.FragmentLoginBinding
import app.capstone.bansosadmin.ui.activities.home.HomeActivity
import app.capstone.bansosadmin.ui.fragments.BaseFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.ext.android.inject


class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val auth: FirebaseAuth by inject()
    private val googleSignInClient: GoogleSignInClient by inject()

    override fun getBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnLogin.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                if (checkEmpty(email, password)) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    requireActivity(),
                                    "Successfully Logged In",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                updateUI()
                            } else {
                                Toast.makeText(
                                    requireActivity(),
                                    "${task.exception?.message}",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                }
            }

            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }

            btnGoogleSignIn.setOnClickListener {
                signIn()
            }

            btnForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                if (task.isSuccessful) {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account?.idToken)
                }

            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                //Navigate to home
                Toast.makeText(
                    requireActivity(),
                    "Successfully Logged In",
                    Toast.LENGTH_LONG
                ).show()
                updateUI()
            }
        }
    }

    private fun updateUI() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


    private fun checkEmpty(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = "Please enter email"
            return false
        }
        if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = "Please enter password"
            return false
        }
        return true
    }

    companion object {
        private const val RC_SIGN_IN = 101
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
}