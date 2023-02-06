package com.example.weatherforecast.view

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.FragmentMainBinding
import com.example.weatherforecast.utils.isPermissionGranted

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                Toast.makeText(
                    activity,
                    "Permission ${if (it) "granted" else "denied"}",
                    Toast.LENGTH_LONG
                ).show()
            }.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}