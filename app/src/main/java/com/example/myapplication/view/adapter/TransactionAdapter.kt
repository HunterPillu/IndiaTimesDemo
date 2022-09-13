package com.example.myapplication.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.DcbTransaction
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class TransactionAdapter(
    private var mList: MutableList<DcbTransaction>,
    private val onListItemClicked: (position: Int, item: DcbTransaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {


    fun addList(list: MutableList<DcbTransaction>) {
        val pos = itemCount
        this.mList.addAll(list)
        notifyItemRangeChanged(pos, list.size)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvMain: MaterialCardView = view.findViewById(R.id.cvMain)
        val tvTitle: MaterialTextView = view.findViewById(R.id.tvTitle)
        val tvPrice: MaterialTextView = view.findViewById(R.id.tvPrice)
        val tvDate: MaterialTextView = view.findViewById(R.id.tvDate)
        val tvType: MaterialTextView = view.findViewById(R.id.tvType)
        //val tvPublishDate: MaterialTextView = view.findViewById(R.id.tvPublishDate)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_billing, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = mList[position]
        viewHolder.tvTitle.text = item.transactionItem
        viewHolder.tvDate.text = item.transactionDate
        viewHolder.tvType.text = item.typeOfTransaction
        viewHolder.tvPrice.text = "₹ ${item.transactionAmount}"

        viewHolder.cvMain.setOnClickListener {
            onListItemClicked(viewHolder.adapterPosition, item)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

}