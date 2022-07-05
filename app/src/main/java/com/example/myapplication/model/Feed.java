package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class Feed {

    @Element(name = "title", required = false)
    @Path("channel")
    private String channelTitle;

    /*@Element(name = "link", required = false)
    @Path("channel")
    private String link;*/

    @Element(name = "description", required = false)
    @Path("channel")
    private String description;

    @Element(name = "language", required = false)
    @Path("channel")
    private String language;

    @Element(name = "copyright", required = false)
    @Path("channel")
    private String copyright;

    @Element(name = "docs", required = false)
    @Path("channel")
    private String docs;

    @Element(name = "image", required = false)
    @Path("channel")
    private ImageIT image;

    @Element(name = "lastBuildDate", required = false)
    @Path("channel")
    private String lastBuildDate;

    @ElementList(name = "item", inline = true)
    @Path("channel")
    private List<Article> articleList;

    /**
     * @return the channelTitle
     */
    public String getChannelTitle() {
        return channelTitle;
    }

    /**
     * @param channelTitle the channelTitle to set
     */
    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    /**
     * @return the articleList
     */
    public List<Article> getArticleList() {
        return articleList;
    }

    /**
     * @param articleList the articleList to set
     */
    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   /* public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }*/

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public ImageIT getImage() {
        return image;
    }

    public void setImage(ImageIT image) {
        this.image = image;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "channelTitle='" + channelTitle + '\'' +
                ",\n description='" + description + '\'' +
               // ",\n link='" + link + '\'' +
                ",\n language='" + language + '\'' +
                ",\n copyright='" + copyright + '\'' +
                ",\n docs='" + docs + '\'' +
                ",\n image=" + image +
                ",\n lastBuildDate='" + lastBuildDate + '\'' +
                ",\n articleList=" + articleList +
                '}';
    }

}
