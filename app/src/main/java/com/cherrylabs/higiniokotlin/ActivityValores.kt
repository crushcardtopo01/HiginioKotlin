package com.cherrylabs.higiniokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_valores.*

class ActivityValores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valores)




        //val dato = bundle.getString("direccion")

        btSalir.setOnClickListener {

            finish()
        }


    }
}