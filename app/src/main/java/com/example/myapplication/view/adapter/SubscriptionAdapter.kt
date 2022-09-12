package com.example.myapplication.view.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Article
import com.example.myapplication.common_util.Util
import com.example.myapplication.common_util.Util.getHtmlText
import com.example.myapplication.model.DcbSubscription
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class SubscriptionAdapter(
    private var mList: MutableList<DcbSubscription>,
    private val onListItemClicked: (position: Int, item: DcbSubscription) -> Unit
) : RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {

    private var mSelectedPos = -1

    fun addList(list: MutableList<DcbSubscription>) {
        val pos = itemCount
        this.mList.addAll(list)
        notifyItemRangeChanged(pos, list.size)
    }

    fun updateSelectedItem(pos: Int) {
        val oldSelection = mSelectedPos
        if (mSelectedPos != -1) {
            notifyItemChanged(oldSelection)
        }
        mSelectedPos = pos
        if (pos != -1) {
            notifyItemChanged(mSelectedPos)
        }
    }

    /*override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (!isLoading && holder.adapterPosition == itemCount - 1) {
            isLoading = true
            onLoadMore()
        }
    }*/

    /*fun updateList(list: List<Article>) {
        this.mList = list
        notifyDataSetChanged()
    }*/

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvMain: MaterialCardView = view.findViewById(R.id.cvMain)
        val tvTitle: MaterialTextView = view.findViewById(R.id.tvTitle)
        val tvPrice: MaterialTextView = view.findViewById(R.id.tvPrice)
        //val tvPublishDate: MaterialTextView = view.findViewById(R.id.tvPublishDate)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_subscription, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = mList[position]
        if (position == mSelectedPos) {
            viewHolder.cvMain.setCardBackgroundColor(
                ContextCompat.getColor(
                    viewHolder.cvMain.context,
                    R.color.colorPrimary
                )
            )

            viewHolder.tvTitle.setTextColor(
                ContextCompat.getColor(
                    viewHolder.tvTitle.context,
                    R.color.textLightPrimary
                )
            )
        } else {
            viewHolder.cvMain.setCardBackgroundColor(
                ContextCompat.getColor(
                    viewHolder.cvMain.context,
                    R.color.header_bg
                )
            )

            viewHolder.tvTitle.setTextColor(
                ContextCompat.getColor(
                    viewHolder.tvTitle.context,
                    R.color.textDarkPrimary
                )
            )


        }
        viewHolder.tvTitle.text = item.subscriptionName
        viewHolder.tvPrice.text = "₹ ${item.subscriptionAmount}"

        viewHolder.cvMain.setOnClickListener {
            onListItemClicked(viewHolder.adapterPosition, item)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun getSelectedItem(): DcbSubscription {
        return mList[mSelectedPos]
    }


}