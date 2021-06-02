package app.capstone.bansosadmin.ui.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getBindings()
        setContentView(binding.root)
    }

    abstract fun getBindings(): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}