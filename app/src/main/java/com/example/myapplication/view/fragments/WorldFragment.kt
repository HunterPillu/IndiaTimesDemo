package com.example.myapplication.view.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.common_util.Util
import com.example.myapplication.viewmodel.CommonViewModel

class WorldFragment : BaseFragment() {


    companion object {
        fun newInstance(tabNum: Int): WorldFragment {
            val frag = WorldFragment();
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
        tvToolbarTitle.setText(R.string.bottom_world)
        tvToolbarDesc.setText(R.string.desc_world)
    }

}