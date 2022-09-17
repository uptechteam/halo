package com.uptech.halo.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uptech.halo.databinding.DonationsFragmentBinding

class DonationFragment : Fragment() {
  private lateinit var binding: DonationsFragmentBinding
  private val epoxyController: DonationsEpoxyController by lazy { DonationsEpoxyController() }


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = DonationsFragmentBinding.inflate(inflater, container, false)
    .also { binding -> this.binding = binding }.root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.donations.adapter = epoxyController.adapter
    epoxyController.setData(
      listOf(
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
      )
    )
  }
}