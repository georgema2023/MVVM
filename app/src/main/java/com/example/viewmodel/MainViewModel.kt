package com.example.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.model.MainModel
import java.util.*

class MainViewModel():ViewModel() {
    val resultObservable = MutableLiveData<ArrayList<MainModel.ResultEntity>>()
    fun getResultObservable():LiveData<ArrayList<MainModel.ResultEntity>> = resultObservable
    lateinit var entityList: ArrayList<MainModel.ResultEntity>

    fun findAddress(mainModel: MainModel){
        entityList = mainModel.findAddress()
        resultObservable.postValue(entityList);
    }
}