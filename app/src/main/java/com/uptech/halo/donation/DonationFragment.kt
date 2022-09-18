package com.uptech.halo.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.uptech.halo.EdgesItemDecoration
import com.uptech.halo.R
import com.uptech.halo.data.FirebaseDataSource
import com.uptech.halo.databinding.DonationsFragmentBinding
import com.uptech.halo.donation.details.DonationDetailsFragment.Companion.DONATION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DonationFragment : Fragment() {
  private lateinit var binding: DonationsFragmentBinding
  private lateinit var lots: List<Donation>
  private val epoxyController: DonationsEpoxyController by lazy {
    DonationsEpoxyController { donationId ->
      findNavController().navigate(
        R.id.donationDetailsFragment,
        Bundle().apply { putSerializable(DONATION, lots.first { lot -> lot.id == donationId }) }
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
      FirebaseDataSource.getAllLots().map { lot ->
        Donation(
          lot.id,
          lot.imageUrl,
          lot.title,
          lot.progress to lot.target
        )
      }.let { donations ->
        lots = donations
        epoxyController.setData(donations)
      }
    }

      /*listOf(
        Donation(
          1,
          "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
          "Nokia",
          50 to 100
        ),
        Donation(
          2,
          "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
          "Nokia",
          50 to 100
        )
      )*/
  }
}