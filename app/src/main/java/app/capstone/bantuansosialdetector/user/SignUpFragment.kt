package app.capstone.bantuansosialdetector.user

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import app.capstone.bantuansosialdetector.MainActivity
import app.capstone.bantuansosialdetector.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordConfirmation = binding.etPasswordConfirmation.text.toString()

            if (checkEmpty(email, password, passwordConfirmation)) {
                if (checkInput(email, password, passwordConfirmation)) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    requireActivity(),
                                    "Successfully Registered",
                                    Toast.LENGTH_LONG
                                ).show()
                                val user = auth.currentUser?.displayName.toString()
                                updateUi()
                            } else {
                                Toast.makeText(
                                    requireActivity(),
                                    "Registration Failed: ${task.exception?.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
        }
    }

    private fun updateUi() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun checkEmpty(email: String, password: String, passConf: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = "Please enter email"
            return false
        }
        if (TextUtils.isEmpty(password)) {
            binding.etPassword.error = "Please enter password"
            return false
        }
        if (TextUtils.isEmpty(passConf)) {
            binding.etPasswordConfirmation.error = "Please enter password confirmation"
            return false
        }
        return true
    }

    private fun checkInput(email: String, password: String, passConf: String): Boolean {
        if (!isEmailValid(email)) {
            binding.etEmail.error = "Please enter a valid email"
            return false
        }
        if (password.length < 6) {
            binding.etPassword.error = "Password length must be more than 6 characters"
            return false
        }
        if (passConf != password) {
            binding.etPasswordConfirmation.error = "Password does not match"
            return false
        }
        return true
    }

    private fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}