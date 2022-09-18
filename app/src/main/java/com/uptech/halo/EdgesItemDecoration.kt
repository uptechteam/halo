package com.uptech.halo

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State

class OffsetItemDecoration : ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
    super.getItemOffsets(outRect, view, parent, state)
    when(parent.getChildAdapterPosition(view)) {
      0 -> {
        outRect.top = outRect.top + 12f.dp().toInt()
        outRect.bottom = outRect.bottom + 8f.dp().toInt()
      }
      parent.adapter?.itemCount -> outRect.bottom + 8f.dp().toInt()
      else -> outRect.bottom + 16f.dp().toInt()
    }
  }
}