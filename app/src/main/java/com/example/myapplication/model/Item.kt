package com.example.myapplication.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item")
class Item {
    @Element(name = "title")
     var title: String? = null

    @Element(name = "link")
     var link: String? = null

    @Element(name = "description")
     var description: String? = null

    @Element(name = "guid")
     var guid: String? = null

    @Element(name = "pubDate")
     var pubDate: String? = null

}
