package com.uptech.halo.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.uptech.halo.R
import com.uptech.halo.data.FirebaseDataSource
import com.uptech.halo.databinding.PaymentFagmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentFragment : Fragment() {
  private lateinit var binding: PaymentFagmentBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = PaymentFagmentBinding.inflate(inflater, container, false)
    .also { binding -> this.binding = binding }.root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    with(binding) {
      pay.setOnClickListener {
        if(!(numberInput.text.isNullOrBlank()
            || dateInput.text.isNullOrBlank()
            || cvvInput.text.isNullOrBlank()
            || amountInput.text.isNullOrBlank())
        ) {
          Integer.parseInt(amountInput.text.toString()).let { bonus ->
            lifecycleScope.launch(Dispatchers.IO) {
              FirebaseDataSource.updateDonatorUserBalance(requireContext(), bonus.toLong())
              FirebaseDataSource.updateLotProgress(arguments?.getString(LOT_ID)!!, bonus.toLong())
                withContext(Dispatchers.Main) {
                  findNavController().popBackStack(R.id.donationsFragment, false)
                }
            }
          }
        }
      }
    }
  }

  companion object {
    const val LOT_ID = "lotId"
  }
}