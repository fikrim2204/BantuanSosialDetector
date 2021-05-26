package app.capstone.bantuansosialdetector.loginandsignup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.capstone.bantuansosialdetector.MainActivity
import app.capstone.bantuansosialdetector.R
import app.capstone.bantuansosialdetector.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {
    private val auth: FirebaseAuth by inject()
    private val googleSignInClient: GoogleSignInClient by inject()

    private var user: FirebaseUser? = null
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
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
                            //Navigate to Home
                            user = auth.currentUser
                            updateUI()
                        } else {
                            Toast.makeText(requireActivity(), "${task.exception?.message}", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnGoogleSignIn.setOnClickListener {
            signIn()
        }

        binding.btnForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account?.id)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
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
                user = auth.currentUser
                updateUI()
            } else {
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                updateUI()
            }
        }
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

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun updateUI() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        private const val TAG = "LoginFragment"
        private const val RC_SIGN_IN = 9001
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}