package app.capstone.bansosadmin.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.capstone.bansosadmin.databinding.FragmentProfileBinding
import app.capstone.bansosadmin.ui.activities.auth.MainActivity
import app.capstone.bansosadmin.ui.fragments.BaseFragment
import app.capstone.bansosadmin.utils.processImage
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val auth: FirebaseAuth by inject()
    override fun getBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = auth.currentUser
        with(binding) {
            if (user?.photoUrl != null) {
                ivImage.processImage(user.photoUrl)
            }

            tvName.text = user?.displayName ?: user?.uid
            tvEmail.text = user?.email

            btnSignOut.setOnClickListener {
                MaterialAlertDialogBuilder(requireActivity()).setTitle("Logout?")
                    .setMessage("Are you sure want to logout?")
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("OK") { _, _ ->
                        auth.signOut()
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }.show()
            }
        }
    }
}
