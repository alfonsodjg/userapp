package com.alfonso.usersapp.ui.modules.users.swipe

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alfonso.usersapp.R

class SwipeToDeleteCallback(
    context: Context,
    private val onSwipedAction: (Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.delete_icon)
    private val maxSwipeDistance = 200f

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipedAction(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val limitedDx = dX.coerceAtLeast(-maxSwipeDistance)


        deleteIcon?.let {
            val iconMargin = (itemView.height - it.intrinsicHeight) / 2
            val iconTop = itemView.top + iconMargin
            val iconLeft = itemView.right - it.intrinsicWidth - iconMargin
            val iconRight = itemView.right - iconMargin
            val iconBottom = iconTop + it.intrinsicHeight
            it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            it.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, limitedDx, dY, actionState, isCurrentlyActive)
    }
}
