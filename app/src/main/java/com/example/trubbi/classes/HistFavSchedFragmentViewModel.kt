package com.example.trubbi.classes

import androidx.lifecycle.ViewModel

class HistFavSchedFragmentViewModel:ViewModel() {
    var number = 0;
    fun setListNumber(num:Int){
        number=num
    }
}