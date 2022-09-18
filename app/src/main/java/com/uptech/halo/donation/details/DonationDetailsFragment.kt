package com.uptech.halo.donation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.uptech.halo.R
import com.uptech.halo.databinding.DonationDetailsFragmentBinding
import com.uptech.halo.payment.PaymentFragment.Companion.LOT_ID

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
    (arguments?.getSerializable(DONATION) as? Donation)?.let { donation ->
      with(binding) {
        donate.setOnClickListener {
          findNavController().navigate(
            R.id.payment,
            Bundle().apply {
              putString(LOT_ID, donation.id)
            }
          )
        }
        Glide.with(root)
          .load(donation.imageUrl)
          .into(image)
        header.text = donation.title
        progress.progress = donation.progress.first.toInt()
        progress.max = donation.progress.second.toInt()
        description.text = donation.description
      }
    }
  }




  companion object {
    val DONATION = "donation"
  }
}