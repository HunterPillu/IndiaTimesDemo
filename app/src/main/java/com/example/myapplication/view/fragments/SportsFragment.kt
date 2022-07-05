package com.example.myapplication.view.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.common_util.Util
import com.example.myapplication.viewmodel.CommonViewModel

class SportsFragment : BaseFragment() {

    companion object {
        fun newInstance(tabNum: Int): SportsFragment {
            val frag = SportsFragment();
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
        tvToolbarTitle.setText(R.string.bottom_sports)
        tvToolbarDesc.setText(R.string.desc_sports)
    }

}