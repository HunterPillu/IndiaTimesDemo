package com.example.myapplication.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "image")
class ItImage {
    @Element(name = "title")
    var title: String? = null

    @Element(name = "link")
    var link: String? = null

    @Element(name = "url")
    var url: String? = null


    override fun toString(): String {
        return "ItImage(title=$title, link=$link, url=$url)"
    }


}
