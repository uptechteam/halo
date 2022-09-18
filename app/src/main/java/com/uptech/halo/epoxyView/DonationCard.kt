package com.uptech.halo.epoxyView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT
import com.bumptech.glide.Glide
import com.uptech.halo.databinding.DonationCardBinding

@ModelView(autoLayout = MATCH_WIDTH_WRAP_HEIGHT)
class DonationCard @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
  private val binding: DonationCardBinding =
    DonationCardBinding.inflate(LayoutInflater.from(context), this, true)

  @ModelProp fun setImage(url: String) =
    Glide.with(binding.root)
      .load(url)
      .into(binding.image)

  @ModelProp fun setTitle(title: CharSequence) {
    binding.header.text = title
  }

  @ModelProp fun setProgress(progress: Pair<Int, Int>)  {
    val (progress, max) = progress
      binding.progress.max = max
      binding.progress.progress = progress
      binding.achieved.text = "$progress UAH"
      binding.target.text = "$max UAH"
  }
}