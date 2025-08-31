package com.gopi.securevault.ui.home

import android.content.Intent
import android.os.Bundle
import com.gopi.securevault.databinding.ActivityHomeBinding
import com.gopi.securevault.ui.BaseActivity
import com.gopi.securevault.ui.aadhar.AadharActivity
import com.gopi.securevault.ui.banks.BanksActivity
import com.gopi.securevault.ui.cards.CardsActivity
import com.gopi.securevault.ui.policies.PoliciesActivity
import com.gopi.securevault.ui.pan.PanActivity
import com.gopi.securevault.ui.voterid.VoterIdActivity
import com.gopi.securevault.ui.license.LicenseActivity

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Existing buttons
        binding.btnBanks.setOnClickListener { startActivity(Intent(this, BanksActivity::class.java)) }
        binding.btnCards.setOnClickListener { startActivity(Intent(this, CardsActivity::class.java)) }
        binding.btnPolicies.setOnClickListener { startActivity(Intent(this, PoliciesActivity::class.java)) }
        binding.btnAadhar.setOnClickListener { startActivity(Intent(this, AadharActivity::class.java)) }
        binding.btnPan.setOnClickListener { startActivity(Intent(this, PanActivity::class.java)) }
        binding.btnVoterId.setOnClickListener { startActivity(Intent(this, VoterIdActivity::class.java)) }
        binding.btnLicense.setOnClickListener { startActivity(Intent(this, LicenseActivity::class.java)) }
    }
}
