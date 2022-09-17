package com.uptech.halo

import android.content.res.Resources
import android.util.TypedValue

fun Float.dp(): Float =
  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)