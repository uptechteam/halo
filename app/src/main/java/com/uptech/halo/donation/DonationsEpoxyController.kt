package com.uptech.halo.donation

import com.airbnb.epoxy.TypedEpoxyController
import com.uptech.halo.epoxyView.donationCard

class DonationsEpoxyController(
  private val onDonationClickListener: (String) -> Unit
) : TypedEpoxyController<List<Donation>>() {

  override fun buildModels(donations: List<Donation>?) {
    donations?.forEach { donation ->
      donationCard {
        id(donation.id)
        image(donation.imageUrl)
        title(donation.title)
        progress(donation.progress)
        onDonateClickListener { this@DonationsEpoxyController.onDonationClickListener(donation.id) }
      }
    }
  }
}