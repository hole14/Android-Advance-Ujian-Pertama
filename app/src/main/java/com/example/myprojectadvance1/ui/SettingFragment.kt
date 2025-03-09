package com.example.myprojectadvance1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.myprojectadvance1.R
import com.example.myprojectadvance1.databinding.FragmentSettingBinding
import com.example.myprojectadvance1.switchmode.SettingPreferences
import com.example.myprojectadvance1.switchmode.SettingViewModel
import com.example.myprojectadvance1.switchmode.ViewModelFactory
import com.example.myprojectadvance1.switchmode.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val switchTheme = view.findViewById<SwitchMaterial>(R.id.switch_theme)

        val mainViewModel =ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        mainViewModel.getThemeSetting().observe(viewLifecycleOwner){isDarkModeActive ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
                binding.modeSetting.text = "Light Mode"
                binding.modeSetting.setTextColor(resources.getColor(android.R.color.white))
                }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
                binding.modeSetting.text = "Dark Mode"
                binding.modeSetting.setTextColor(resources.getColor(android.R.color.black))

            }
        }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}