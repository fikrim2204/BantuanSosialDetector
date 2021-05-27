package app.capstone.bantuansosialdetector.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.capstone.bantuansosialdetector.LoginActivity
import app.capstone.bantuansosialdetector.R
import app.capstone.bantuansosialdetector.databinding.FragmentHomeBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = Firebase.auth.currentUser
        with(binding) {
            if (user?.photoUrl == null) {
                ivProfile.load(R.drawable.profile_placeholder) {
                    transformations(CircleCropTransformation())
                }
            } else {
                ivProfile.load(user.photoUrl) {
                    placeholder(R.drawable.profile_placeholder).transformations(CircleCropTransformation())
                        .crossfade(true)
                }
            }
            tvUsername.text = user?.displayName ?: user?.uid
            tvEmail.text = user?.email

            if (user?.providerData?.size == 2) {
                if (user.providerData[1]?.providerId == "google.com") {
                    btnChangeProfile.text = getString(R.string.set_up_password)
                }
            }

            btnChangeProfile.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_updatePasswordFragment)
            }
            btnSignOut.setOnClickListener {
                MaterialAlertDialogBuilder(requireActivity()).setTitle("Logout?")
                    .setMessage("Are you sure want to logout?")
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("OK") { _, _ ->
                        Firebase.auth.signOut()
                        val intent = Intent(requireActivity(), LoginActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }.show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}