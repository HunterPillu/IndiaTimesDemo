package com.example.myapplication.view.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.common_util.Util
import com.example.myapplication.viewmodel.CommonViewModel

class IndiaFragment : BaseFragment() {


    companion object {
        fun newInstance(tabNum: Int): IndiaFragment {
            val frag = IndiaFragment();
            val bundle = Bundle()
            bundle.putInt(Util.KEY_TAB_NUMBER, tabNum)
            frag.arguments = bundle
            return frag
        }
    }

    override fun setUpViewModel() {
        mViewModel = ViewModelProvider(this)[CommonViewModel::class.java]
    }

    override fun setUpToolbar() {
        tvToolbarTitle.setText(R.string.bottom_india)
        tvToolbarDesc.setText(R.string.desc_india)
    }
}