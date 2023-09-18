package com.example.lahagorapracticaltask.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.lahagorapracticaltask.R
import com.example.lahagorapracticaltask.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    var binding: ActivityDashboardBinding? = null
    private lateinit var parentNavController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        initViews()
    }

    private fun initViews() {
        parentNavController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding?.lifecycleOwner = this
    }
}