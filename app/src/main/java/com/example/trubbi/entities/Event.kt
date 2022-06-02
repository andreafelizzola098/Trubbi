package com.example.trubbi.entities


class Event(
    name: String?,
    date: String?,
    time: String?,
    detail: String?,
    address: String?,
    urlImage: String?
) {
    var name: String = ""
    var date: String = ""
    var time: String = ""
    var detail: String = ""
    var address: String = ""
    var urlImage: String = ""

    init {
        this.name = name!!
        this.date = date!!
        this.time = time!!
        this.detail = detail!!
        this.address = address!!
        this.urlImage = urlImage!!
    }
}