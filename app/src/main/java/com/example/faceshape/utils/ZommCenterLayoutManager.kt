package com.example.faceshape.utils

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler




class ZommCenterLayoutManager(context: Context) : LinearLayoutManager(context,HORIZONTAL, false) {

    // Shrink the cards around the center up to 50%
    private val mShrinkAmount = 0.25f

    // The cards will be at 50% when they are 75% of the way between the
    // center and the edge.
    private val mShrinkDistance = 1.0f
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        val scrolled =  super.scrollHorizontallyBy(dx, recycler, state)

        val midpoint = width / 2f
        val d0 = 0f
        val d1: Float = mShrinkDistance * midpoint
        val s0 = 1f
        val s1: Float = 1f - mShrinkAmount

        for (i in 0 until childCount) {
            val child: View? = getChildAt(i)
            val childMidpoint = (getDecoratedRight(child!!) + getDecoratedLeft(child)) / 2f
            val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
            val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
            child.setScaleX(scale)
            child.setScaleY(scale)
        }

        return scrolled
    }

    override fun onLayoutChildren(recycler: Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        scrollHorizontallyBy(0, recycler, state)
    }
}