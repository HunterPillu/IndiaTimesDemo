package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Article
import com.example.myapplication.common_util.Util
import com.example.myapplication.common_util.Util.getHtmlText
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class NewsAdapter(
    private var mList: MutableList<Article>,
    private val onNewsItemClicked: (position: Int, item: Article) -> Unit,
    private val onLoadMore: () -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    var isLoading = false


    fun addList(list: List<Article>) {
        val pos = itemCount
        this.mList.addAll(list)
        notifyItemRangeChanged(pos, list.size)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (!isLoading && holder.adapterPosition == itemCount - 1) {
            isLoading = true
            onLoadMore()
        }
    }

    /*fun updateList(list: List<Article>) {
        this.mList = list
        notifyDataSetChanged()
    }*/

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvMain: MaterialCardView = view.findViewById(R.id.cvMain)
        val tvTitle: MaterialTextView = view.findViewById(R.id.tvTitle)
        val tvDesc: MaterialTextView = view.findViewById(R.id.tvDesc)
        val tvPublishDate: MaterialTextView = view.findViewById(R.id.tvPublishDate)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_news, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = mList[position]
        viewHolder.tvTitle.text = item.title
        viewHolder.tvDesc.text = getHtmlText(item.description)
        viewHolder.tvPublishDate.text = Util.getFormattedDateString(item.pubDate)

        viewHolder.cvMain.setOnClickListener {
            onNewsItemClicked(viewHolder.adapterPosition, item)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

}