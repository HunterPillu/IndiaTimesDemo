package com.example.myapplication.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "channel")
class Channel {


    @Element(name = "title")
    var title: String? = null

    @Element(name = "description")
    var description: String? = null

    @Element(name = "link")
    var link: String? = null

    @Element(name = "language")
    var language: String? = null

    @Element(name = "copyright")
    var copyright: String? = null

    @Element(name = "docs")
    var docs: String? = null

    @Attribute(required = false)
    var image: ItImage? = null

    @Element(name = "lastBuildDate")
    var lastBuildDate: String? = null

    @Attribute(name = "item")
    var item: Array<Item>? = null

    override fun toString(): String {
        return "Channel(title=$title, description=$description, link=$link, language=$language, copyright=$copyright, docs=$docs, image=${image.toString()}, lastBuildDate=$lastBuildDate, item=${item?.contentToString()})"
    }


}