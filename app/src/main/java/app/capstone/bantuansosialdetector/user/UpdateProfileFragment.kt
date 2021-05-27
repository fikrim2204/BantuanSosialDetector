package app.capstone.bantuansosialdetector.user

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import app.capstone.bantuansosialdetector.R
import app.capstone.bantuansosialdetector.databinding.FragmentUpdateProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class UpdateProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        with(binding) {
            btnChangeUsername.setOnClickListener {
                val username = etUsername.text.toString()
                if (checkEmptyUsername(username)) {
                    val profileUpdate = userProfileChangeRequest {
                        displayName = username
                    }
                    auth.currentUser?.updateProfile(profileUpdate)?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                requireActivity(),
                                getString(R.string.profile_updated),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            val user = auth.currentUser
            if (user?.providerData?.size == 2) {
                if (user.providerData[1]?.providerId == "google.com") {
                    btnChangeProfile.text = getString(R.string.set_up_password)
                }
            }

            btnChangeProfile.setOnClickListener {
                val password = etPassword.text.toString()
                val passwordConfirmation = etPasswordConfirmation.text.toString()
                if (checkEmpty(password, passwordConfirmation)) {
                    if (checkInput(password, passwordConfirmation)) {
                        auth.currentUser?.updatePassword(password)
                            ?.addOnCompleteListener(requireActivity()) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        requireActivity(),
                                        "Password changes successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        requireActivity(),
                                        "Password not changed",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }
                            }
                    }
                }
            }
        }
    }

    private fun checkEmptyUsername(username: String): Boolean {
        if (TextUtils.isEmpty(username)) {
            binding.etUsername.error = "Please enter username"
            return false
        }
        return true
    }

    private fun checkEmpty(password: String, passConf: String): Boolean {

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

    private fun checkInput(password: String, passConf: String): Boolean {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}