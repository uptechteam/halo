package com.uptech.halo.donation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.uptech.halo.R
import com.uptech.halo.data.FirebaseDataSource
import com.uptech.halo.databinding.DonationDetailsFragmentBinding
import com.uptech.halo.payment.PaymentFragment.Companion.LOT_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DonationDetailsFragment : Fragment() {
  private lateinit var binding: DonationDetailsFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = DonationDetailsFragmentBinding.inflate(inflater, container, false)
    .also { binding -> this.binding = binding }.root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.close.setOnClickListener {
      findNavController().popBackStack()
    }
    arguments?.getString(DONATION_ID)?.let { donationId ->
      lifecycleScope.launch(Dispatchers.IO) {
        FirebaseDataSource.getAllLots()
          .first { lot -> lot.id == donationId }
          .let { donation ->
            withContext(Dispatchers.Main) {
              with(binding) {
                donate.setOnClickListener {
                  findNavController().navigate(
                    R.id.payment,
                    Bundle().apply {
                      putString(LOT_ID, donationId)
                    }
                  )
                }
                Glide.with(root)
                  .load(donation.imageUrl)
                  .into(image)
                header.text = donation.title
                progress.progress = donation.progress.toInt()
                progress.max = donation.progress.toInt()
                description.text = donation.targetDescription
              }
            }
          }
      }
    }
  }

  companion object {
    val DONATION_ID = "donation_id"
  }
}