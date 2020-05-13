package com.vision.betting

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.vision.betting.adapters.MyAdapter
import com.vision.betting.models.RvModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var arrayList:ArrayList<RvModel>
    lateinit var myAdapter:MyAdapter
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayList = ArrayList()
        mAuth = FirebaseAuth.getInstance()

        rlLogout.setOnClickListener {
            logoutDialog()
        }

        adapterSet()
    }

    private fun adapterSet(){
        arrayList.add(RvModel(R.drawable.cricket,"IPL","Cricket"))
        arrayList.add(RvModel(R.drawable.football,"FIFA","Football"))
        arrayList.add(RvModel(R.drawable.basketball,"TBT Championship","Basketball"))
        arrayList.add(RvModel(R.drawable.hockey,"Men's Pro ","Hockey"))
        arrayList.add(RvModel(R.drawable.badminton,"BWF","Badminton"))

        rvList.layoutManager = LinearLayoutManager(this)
        myAdapter = MyAdapter(this,arrayList)
        rvList.adapter = myAdapter
    }

    private fun logoutDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage(R.string.logout_dialog_message)
        builder.setPositiveButton(R.string.yes,
            DialogInterface.OnClickListener { dialog, id ->

                dialog.dismiss()
                mAuth?.signOut()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP + Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            })
        builder.setNegativeButton(R.string.no,
            DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
        val alertDialog = builder.create()
        alertDialog.show()
    }
}
