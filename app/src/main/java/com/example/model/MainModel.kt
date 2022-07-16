package com.example.model

import java.util.*

class MainModel {

    fun findAddress():ArrayList<ResultEntity>{
        var mList = ArrayList<ResultEntity>()
        mList.add(ResultEntity("1","1","1","1"))
        mList.add(ResultEntity("2","2","2","2"))
        return mList
    }

    class ResultEntity(val title:String, val rating:String, val date:String, val year:String)
}