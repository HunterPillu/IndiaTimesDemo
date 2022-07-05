package com.example.myapplication.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root


@Root(name = "rss", strict = false)
class RssFeed {

    /*@Element(name = "rss")
    var rss: Rss? = null*/
    /* override fun toString(): String {
         return "RssFeed(rss=$rss)"
     }*/

    /*@Path("rss")
    var channel: Channel? = null
    override fun toString(): String {
        return "RssFeed( channel=${channel.toString()})"
    }*/

    @Element(name = "title",required = false)
    @Path("channel")
    var channelTitle: String? = null

    override fun toString(): String {
        return "RssFeed(channelTitle=$channelTitle)"
    }

    /*@ElementList(name = "item", inline = true)
    @Path("channel")
     var articleList: List<Item>? = null*/


    //var version: String? = null


}