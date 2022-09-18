package com.uptech.halo.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.uptech.halo.R
import com.uptech.halo.data.FirebaseDataSource
import com.uptech.halo.databinding.DonationsFragmentBinding
import com.uptech.halo.donation.details.DonationDetailsFragment.Companion.DONATION
import com.uptech.halo.models.Lot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DonationFragment : Fragment() {
  private lateinit var binding: DonationsFragmentBinding
  private lateinit var lots: List<Lot>
  private val epoxyController: DonationsEpoxyController by lazy {
    DonationsEpoxyController { donationId ->
      findNavController().navigate(
        R.id.donationDetailsFragment,
        Bundle().apply {
          putSerializable(
            DONATION,
            lots.first { lot -> lot.id == donationId }
              .let { lot ->
                com.uptech.halo.donation.details.Donation(
                  lot.id,
                  lot.imageUrl,
                  lot.title,
                  lot.progress to lot.target,
                  lot.targetDescription
                )
              }
          )
        }
      )
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = DonationsFragmentBinding.inflate(inflater, container, false)
    .also { binding -> this.binding = binding }.root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.donations.adapter = epoxyController.adapter
    lifecycleScope.launch(Dispatchers.IO) {
      FirebaseDataSource.getAllLots()
        .also { donations ->
          lots = donations
        }.map { lot ->
        Donation(
          lot.id,
          lot.imageUrl,
          lot.title,
          lot.progress to lot.target
        )
      }.let { donations -> epoxyController.setData(donations) }
    }
  }
}