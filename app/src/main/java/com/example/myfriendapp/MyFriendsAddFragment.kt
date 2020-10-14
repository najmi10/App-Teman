package com.example.myfriendapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friends_add_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyFriendsAddFragment : Fragment(){
    companion object{
        fun newInstance() : MyFriendsAddFragment{
            return MyFriendsAddFragment()
        }
}
    private var namaInput : String = ""
    private var emailInput : String = ""
    private var telpInput : String = ""
    private var alamatInput : String = ""
    private var genderInput : String = ""

    private var db: DatabaseApp? = null
    private var myFriendDao: MyFriendDao? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friends_add_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }

    private fun initLocalDB(){
        db = DatabaseApp.getDatabaseApp(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    private fun initView(){
        btnSave.setOnClickListener{ validasiInput() }
           setDataSpinnerGender()
    }

    private fun setDataSpinnerGender(){
        val adapter = ArrayAdapter.createFromResource(activity!!, R.array.genderlist,
            android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter
    }

    private fun validasiInput(){
        namaInput = edtName.text.toString()
        emailInput = edtEmail.text.toString()
        telpInput = edtTelp.text.toString()
        alamatInput = edtAddress.text.toString()
        genderInput = spinnerGender.selectedItem.toString()

        when{
            namaInput.isEmpty() -> edtName.error = "Nama tidak boleh kosong nanti gabisa kenalan"
            genderInput.equals("Pilih kelamin") -> tampilToast("Kelamin harus dipilih, tidak ada pilihan waria")
            emailInput.isEmpty() ->edtEmail.error = "Email harus diisi"
            telpInput.isEmpty() -> edtTelp.error = "Nomor Telepon tidak boleh kosong nanti buat kenalan di wasap"
            alamatInput.isEmpty() -> edtAddress.error = "Alamat harus diisi sabi nanti mampir kerumah"

            else ->{
                val teman = MyFriend(nama = namaInput, kelamin = genderInput, email = emailInput,
                telp = telpInput, alamat = alamatInput)
                tambahDataTeman(teman)
            }
        }
    }
    private fun tambahDataTeman(teman: MyFriend) : Job{
        return GlobalScope.launch {
            myFriendDao?.tambahTeman(teman)
            (activity as MainActivity).tampilMyFriendFragment()
        }
    }
    private fun tampilToast(message: String){
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}