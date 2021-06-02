package app.capstone.bansosadmin.ui.fragments.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import app.capstone.bansosadmin.databinding.FragmentProfileBinding
import app.capstone.bansosadmin.ui.fragments.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun getBindings(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

}