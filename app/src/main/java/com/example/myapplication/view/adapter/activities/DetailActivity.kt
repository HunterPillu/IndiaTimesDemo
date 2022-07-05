package com.example.myapplication.view.adapter.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.example.myapplication.R
import com.example.myapplication.model.Article
import com.example.myapplication.utils.Util
import com.google.android.material.textview.MaterialTextView


class DetailActivity : AppCompatActivity() {

    lateinit var mArticle: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mArticle = intent.getParcelableExtra(Util.KEY_ARTICLE)!!
        val newsType = intent.getIntExtra(Util.KEY_NEWS_TYPE, 0)
        setToolbarTexts(newsType)


        val tvTitle: MaterialTextView = findViewById(R.id.tvTitle)
        val tvDesc: MaterialTextView = findViewById(R.id.tvDesc)
        val tvPublishDate: MaterialTextView = findViewById(R.id.tvPublishDate)
        val tvReadMore: MaterialTextView = findViewById(R.id.tvReadMore)

        tvTitle.text = mArticle.title
        tvDesc.text = Util.getHtmlText(mArticle.description)
        tvPublishDate.text = Util.getFormattedDateString(mArticle.pubDate)

        tvReadMore.setOnClickListener { openLink() }

    }

    private fun setToolbarTexts(newsType: Int) {

        val ivToolbarBack: AppCompatImageView = findViewById(R.id.ivToolbarBack)
        val tvToolbarTitle: MaterialTextView = findViewById(R.id.tvToolbarTitle)
        val tvToolbarDesc: MaterialTextView = findViewById(R.id.tvToolbarDesc)
        ivToolbarBack.setOnClickListener { onBackPressed() }


        when (newsType) {
            0 -> {
                tvToolbarTitle.setText(R.string.bottom_top_stories)
                tvToolbarDesc.setText(R.string.desc_top_stories_detail)
            }
            1 -> {
                tvToolbarTitle.setText(R.string.bottom_india)
                tvToolbarDesc.setText(R.string.desc_india_detail)
            }
            2 -> {
                tvToolbarTitle.setText(R.string.bottom_world)
                tvToolbarDesc.setText(R.string.desc_world_detail)
            }
            else -> {
                tvToolbarTitle.setText(R.string.bottom_sports)
                tvToolbarDesc.setText(R.string.desc_sports_detail)
            }

        }

    }

    private fun openLink() {
        val url = mArticle.link
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}