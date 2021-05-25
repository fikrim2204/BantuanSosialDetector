package app.capstone.bantuansosialdetector.loginandsignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import app.capstone.bantuansosialdetector.R
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword("email", "password")
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireActivity(), "Successfully Registered", Toast.LENGTH_LONG)
                        .show()
                    //Navigate to Home
                } else {
                    Toast.makeText(requireActivity(), "Registration Failed", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}