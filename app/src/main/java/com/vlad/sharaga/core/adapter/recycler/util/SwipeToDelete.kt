package com.vlad.sharaga.core.adapter.recycler.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vlad.sharaga.R
import com.vlad.sharaga.core.util.toPx

class SwipeToDelete(
    private val targetViewType: Int,
    private val onItemDelete: (Int) -> Unit,
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.ACTION_STATE_IDLE,
    ItemTouchHelper.LEFT
) {

    private val p = Paint().also { it.color = 0xFFF40202.toInt() }
    private var icon: Bitmap? = null

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onItemDelete(viewHolder.adapterPosition)
    }

    override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return if (viewHolder.itemViewType != targetViewType) {
            ItemTouchHelper.ACTION_STATE_IDLE
        } else {
            super.getSwipeDirs(recyclerView, viewHolder)
        }
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = 0.3f

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            val itemView: View = viewHolder.itemView

            // Draw background
            val cornerRadius = itemView.context.toPx(8)
            c.drawRoundRect(
                itemView.right.toFloat() + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat(),
                cornerRadius,
                cornerRadius,
                p
            )

            //Draw icon
            if (icon == null) {
                ResourcesCompat.getDrawable(itemView.resources, R.drawable.ic_trash, null)?.let {
                    val drawable = it
                    val bitmap = Bitmap.createBitmap(
                        drawable.intrinsicWidth,
                        drawable.intrinsicHeight,
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(bitmap)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                    icon = bitmap
                }
            }
            val ic = icon ?: error("Icon not found")
            val iconMarginRight = (dX * -0.1f).coerceAtMost(70f).coerceAtLeast(0f)
            c.drawBitmap(
                ic,
                itemView.right.toFloat() - iconMarginRight - ic.width,
                itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - ic.height) / 2,
                p
            )
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}