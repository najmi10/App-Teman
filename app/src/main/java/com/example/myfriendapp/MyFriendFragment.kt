package com.example.myfriendapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friends_fragment.*

class MyFriendFragment : Fragment() {

    companion object {
        fun newInstance(): MyFriendFragment {
            return MyFriendFragment()
        }
    }

    private var listTeman : List<MyFriend>? = null
    private var db: DatabaseApp? = null
    private var myFriendDao: MyFriendDao? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friends_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initLocalDB()
    }

    private fun initLocalDB(){
        db = DatabaseApp.getDatabaseApp(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        fabAddFriend.setOnClickListener {
            (activity as MainActivity).tampilMyFriendsAddFragment()
        }
        ambilDataTeman()
    }

    private fun ambilDataTeman(){
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(this, Observer {
            r -> listTeman = r

            when{
                listTeman?.size == 0 -> tampilToast("Belum ada data teman")

                else ->{
                    tampilTeman()
                }
            }
        })
    }

    private fun tampilToast(message: String){
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    private fun tampilTeman(){
        listMyFriend.layoutManager = LinearLayoutManager(activity)
        listMyFriend.adapter = MyFriendAdapter(activity!!, listTeman!!)
    }


    //private fun simulasiDataTeman(){
        //listTeman.add(MyFriend("Memoreza", "Laki-laki",
        //"memoreza@gmail.com", "081345654123", "Tuban"))
        //listTeman.add(MyFriend("Rensi", "Perempuan",
            //"rensi@gmail.com", "082123409867", "Kediri"))
    //}

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}