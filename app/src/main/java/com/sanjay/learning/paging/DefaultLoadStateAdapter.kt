package com.sanjay.learning.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.learning.R

/**
 * A LoadStateAdapter implementation that takes care of showing loading and error states
 * when adding as a footer to a PagingDataAdapter instance, it offers support for initial
 * load states by a workaround documented down below.
 */
class DefaultLoadStateAdapter(
    private val retryCallback: () -> Unit
) : LoadStateAdapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {
        holder.itemView.apply {
            val retryGroup = findViewById<View>(R.id.retryGroup)
            val pbLoading = findViewById<View>(R.id.pbLoading)
            val tvState = findViewById<TextView>(R.id.tvState)
            val btRetry = findViewById<TextView>(R.id.btRetry)

            retryGroup.isVisible = loadState is LoadState.Error
            tvState.isVisible = loadState is LoadState.Error
            pbLoading.isVisible = loadState is LoadState.Loading

            if (loadState is LoadState.Error) {
                tvState.text = holder.itemView.context.getString(R.string.no_internet_connection)
            }

            btRetry.setOnClickListener { retryCallback() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(layoutIdBasedOnInitialState(), parent, false)
        ) {}
    }

    override fun getStateViewType(loadState: LoadState): Int = layoutIdBasedOnInitialState()

    // Use it to know if the first loaded page is empty
    fun isInitialLoadEmpty(loadState: CombinedLoadStates, adapterItemCount: Int) =
        adapterItemCount == 0 &&
                loadState.append != LoadState.Loading &&
                loadState.refresh != LoadState.Loading &&
                loadState.append !is LoadState.Error &&
                loadState.refresh !is LoadState.Error

    private var firstTimeSinceLoadStateWasInInitialLoad = false
    private var isLoadStateInInitialLoad = true

    /**
     * Call DefaultLoadStateAdapter.handleInitialLoad from PagingDataAdapter.addLoadStateListener
     * (see ActivityTabFragment) when you want that the loading and error views be shown from the very beginning,
     * meaning in the initial load too and not only as the last element of the list during newt pages load,
     * this will avoid having to duplicate views and logic inside the fragment.
     */
    fun handleInitialLoad(newLoadState: CombinedLoadStates, recyclerView: RecyclerView) {
        // After manually pushing a LoadState to the adapter, the recycler view is scrolled to the second page for no
        // reason, that's why we need this workaround to readjust it to the first page again.
        if (firstTimeSinceLoadStateWasInInitialLoad) {
            firstTimeSinceLoadStateWasInInitialLoad = false
            recyclerView.scrollToPosition(0)
        }

        // The adapter is not called by the system during the initial load, that's why we have to fallback
        // manually this initial event.
        // For more info, check the issue opened: https://issuetracker.google.com/issues/172424000
        isLoadStateInInitialLoad = newLoadState.refresh is LoadState.Error || newLoadState.refresh is LoadState.Loading
        if (isLoadStateInInitialLoad) {
            loadState = newLoadState.refresh
            firstTimeSinceLoadStateWasInInitialLoad = true
        } else {
            loadState = newLoadState.append
        }
    }

    // Show vertical centered layout only when the item is rendered during the initial load
    // as at that time there are no items in the list.
    private fun layoutIdBasedOnInitialState() = if (isLoadStateInInitialLoad) {
        R.layout.page_state_item_vetical_centered
    } else {
        R.layout.page_state_item
    }
}
