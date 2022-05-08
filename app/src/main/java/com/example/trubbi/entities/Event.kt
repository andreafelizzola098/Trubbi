package com.example.trubbi.entities

class Event(name: String?, date: String?, time: String?, detail: String?, address: String?) {
    var name:String = ""
    var date:String = ""
    var time:String = ""
    var detail:String = ""
    var address:String = ""

    class Events{

    }

    init {
        this.name = name!!
        this.date = date!!
        this.time = time!!
        this.detail = detail!!
        this.address = address!!
    }
}