package com.example.trubbi.entities


class Event(
    id: Long,
    name: String?,
    date: String?,
    time: String?,
    detail: String?,
    address: String?,
    urlImage: String?
) {
    var id: Long = 0
    var name: String = ""
    var date: String = ""
    var time: String = ""
    var detail: String = ""
    var address: String = ""
    var urlImage: String = ""

    init {
        this.id = id
        this.name = name!!
        this.date = date!!
        this.time = time!!
        this.detail = detail!!
        this.address = address!!
        this.urlImage = urlImage!!
    }
}