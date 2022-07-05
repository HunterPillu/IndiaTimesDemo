package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Article
import com.example.myapplication.utils.Util
import com.example.myapplication.utils.Status
import com.example.myapplication.view.adapter.NewsAdapter
import com.example.myapplication.view.adapter.activities.DetailActivity
import com.google.android.material.textview.MaterialTextView

abstract class BaseFragment : Fragment(R.layout.top_stories_fragment) {
    private val TAG: String = BaseFragment::class.java.name
    lateinit var mViewModel: CommonViewModel
    private val mList: ArrayList<Article> = ArrayList()
    private lateinit var mAdapter: NewsAdapter
    private var currentTabNumber: Int = 0
    lateinit var pbProgressBar: ProgressBar
    lateinit var pbLoadMore: ProgressBar

    lateinit var ivToolbarBack: AppCompatImageView
    lateinit var tvToolbarTitle: MaterialTextView
    lateinit var tvToolbarDesc: MaterialTextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentTabNumber = arguments?.getInt(Util.KEY_TAB_NUMBER)!!
        initView()
        setUpToolbar()
        setUpViewModel()
        observeLiveData()
        fetchArticles()
    }


    private fun initView() {
        pbLoadMore = view?.findViewById(R.id.pbLoadMore)!!
        pbProgressBar = view?.findViewById(R.id.pbProgress)!!
        ivToolbarBack = view?.findViewById(R.id.ivToolbarBack)!!
        tvToolbarTitle = view?.findViewById(R.id.tvToolbarTitle)!!
        tvToolbarDesc = view?.findViewById(R.id.tvToolbarDesc)!!
        ivToolbarBack.setOnClickListener { activity?.finish() }
        mList.clear()
        mAdapter = NewsAdapter(mList, ::onNewsItemClicked, ::onLoadMore)
        val rvNews: RecyclerView? = view?.findViewById(R.id.rvNews)
        rvNews?.adapter = mAdapter
    }

    private fun observeLiveData() {
        mViewModel.statusLiveData.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                    if (it.data == Util.CODE_LOAD_MORE) {
                        pbLoadMore.visibility = View.VISIBLE
                    } else {
                        pbProgressBar.visibility = View.VISIBLE
                    }
                }
                Status.SUCCESS -> {
                    if (it.data == Util.CODE_LOAD_MORE) {
                        pbLoadMore.visibility = View.GONE
                        mAdapter.isLoading = false
                    } else {
                        pbProgressBar.visibility = View.GONE
                        //Log.e(TAG, "${mViewModel.mFeedResult.toString()}")
                        mAdapter.addList(mViewModel.mFeedResult.articleList)
                    }
                }
                Status.ERROR -> {
                    pbLoadMore.visibility = View.GONE
                    pbProgressBar.visibility = View.GONE

                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }
    }

    private fun fetchArticles() {
        mViewModel.fetchArticle(currentTabNumber)
    }

    private fun onLoadMore() {
        Log.d("$TAG/$currentTabNumber", "onLoadMore")
        mViewModel.onLoadMore(currentTabNumber)
    }

    private fun onNewsItemClicked(position: Int, item: Article) {
        Log.d("$TAG/$currentTabNumber", "onLoadMore")

        val detailIntent = Intent(context, DetailActivity::class.java).apply {
            putExtra(Util.KEY_NEWS_TYPE, currentTabNumber)
            putExtra(Util.KEY_ARTICLE, item)
        }
        startActivity(detailIntent)

    }

    abstract fun setUpViewModel()
    abstract fun setUpToolbar()


}