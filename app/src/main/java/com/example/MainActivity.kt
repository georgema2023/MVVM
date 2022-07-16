package com.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.model.MainModel
import com.example.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mMainViewModel: MainViewModel
    private lateinit var addressAdapter: AddressAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        addressAdapter = AddressAdapter { goToDetailActivity(it) }
        findViewById<RecyclerView>(R.id.main_activity_recyclerView).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.main_activity_recyclerView).adapter = addressAdapter

        mMainViewModel.getResultObservable().observe(this, androidx.lifecycle.Observer {
            hideProgressBar()
            showResult(it)

        })

        findViewById<Button>(R.id.main_activity_button).setOnClickListener {
            mMainViewModel.findAddress(MainModel())
        }
    }

    fun showProgressBar(){
        findViewById<ProgressBar>(R.id.main_activity_progress_bar).visibility = View.VISIBLE
    }

    fun hideProgressBar(){
        findViewById<ProgressBar>(R.id.main_activity_progress_bar).visibility = View.GONE
    }

    fun showResult(list:ArrayList<MainModel.ResultEntity> ){
        addressAdapter.updateList(list)
        addressAdapter.notifyDataSetChanged()
    }

    fun goToDetailActivity(item: MainModel.ResultEntity) {
        var bundle = Bundle()
        bundle.putString(DetailActivity.Constants.RATING, item.rating)
        bundle.putString(DetailActivity.Constants.TITLE, item.title)
        bundle.putString(DetailActivity.Constants.YEAR, item.year)
        bundle.putString(DetailActivity.Constants.DATE, item.date)
        var intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("extra",bundle)
        startActivity(intent)
    }

    class AddressAdapter(val onClick:(item: MainModel.ResultEntity)->Unit): RecyclerView.Adapter<AddressAdapter.Holder>() {
        var mList:List<MainModel.ResultEntity> = ArrayList()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
            return Holder(view)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.itemView.findViewById<TextView>(R.id.item_textView).text = "${mList[position].year}:${mList[position].title}"
            holder.itemView.setOnClickListener{onClick(mList[position])}
        }

        override fun getItemCount(): Int {
            return mList.size
        }

        fun updateList(list:List<MainModel.ResultEntity>){
            mList = list;
        }
        class Holder(itemView: View): RecyclerView.ViewHolder(itemView)
    }
}