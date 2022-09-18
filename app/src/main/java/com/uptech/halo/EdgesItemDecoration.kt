package com.uptech.halo

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State

class EdgesItemDecoration : ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
    super.getItemOffsets(outRect, view, parent, state)
    outRect.right = outRect.right + 20f.dp().toInt()
    outRect.left = outRect.left - 20f.dp().toInt()

    when(parent.getChildAdapterPosition(view)) {
      0 -> outRect.top = outRect.top + 20f.dp().toInt()
      parent.adapter?.itemCount -> outRect.bottom - 20f.dp().toInt()
    }
  }
}