package com.cherrylabs.higiniokotlin

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import android.provider.MediaStore
import android.media.MediaPlayer
import android.net.Uri

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private lateinit var  mpGlobal: MediaPlayer
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- Code of Pratica 06

        val lista = arrayOf("Sumar","Restar","Multiplicar","Dividir")

        val adaptador1 = ArrayAdapter<String> (this,android.R.layout.simple_spinner_item, lista)
        spinner.adapter = adaptador1

        btPractica4BotonOperar.setOnClickListener {
            when(spinner.selectedItem.toString()){
                //"sumar" -> tv1.text = "Resultado: ${et1.text.toString().toInt() + et2.text.toString().toInt()}"
                //
                "Sumar" -> tvPractica4Reultado.text = "Resultado: ${tvPractica4numero1.text.toString().toInt() + tvPractica4numero2.text.toString().toInt() } "
                "Restar" -> tvPractica4Reultado.text = "Resultado: ${tvPractica4numero1.text.toString().toInt() - tvPractica4numero2.text.toString().toInt() } "
                "Multiplicar" -> tvPractica4Reultado.text = "Resultado: ${tvPractica4numero1.text.toString().toInt() * tvPractica4numero2.text.toString().toInt() } "
                "Dividir" -> tvPractica4Reultado.text = "Resultado: ${tvPractica4numero1.text.toString().toInt() / tvPractica4numero2.text.toString().toInt() } "
            }
        }

        // ----- End of code Practica 06


















        // ----- Code Practica 10

        btPractica10Verificar.setOnClickListener {
            if (tvPractica10cadena2.text.length == 0) {
                Toast.makeText(this,"La claver no puede ser vacia", Toast.LENGTH_LONG).show()
            }
        }

        // ----- End code Practica 10


        cambiarActivity.setOnClickListener {
            val intent1 = Intent(this,MainActivity2::class.java)
            startActivity(intent1)




            // --- Practica 12





            // --- end practica 12
        }

        cambiarActivityValores.setOnClickListener {


            if (tvClave.text.toString() == "abc123"){

                val intent2 = Intent(this,ActivityValores::class.java)

                startActivity(intent2)
            }else{
                Toast.makeText(this,"La contraseña es incorrecta", Toast.LENGTH_LONG).show()

            }



        }


        // ------------------ Practica 15

        btGuardar.setOnClickListener {


            try {
                // val tarjeta = Environment.getExternalFilesDir()
                val file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), etpractica15t1.text.toString())
                //val file = File(tarjeta.getAbsolutePath(), et1.text.toString())
                val osw = OutputStreamWriter(FileOutputStream(file))
                osw.write(etPractica15t2.text.toString())
                osw.flush()
                osw.close()
                Toast.makeText(this, "Los datos fueron grabados correctamente", Toast.LENGTH_SHORT)
                    .show()
                etpractica15t1.setText("")
                etPractica15t2.setText("")

            }catch (ioe: IOException){

            }

        }

        btRecuperar.setOnClickListener {
            val file =File( getExternalFilesDir( Environment.DIRECTORY_DOCUMENTS), etpractica15t1.text.toString())

            try{

                val fIn = FileInputStream(file)
                val archivo = InputStreamReader(fIn)
                val br = BufferedReader(archivo)
                var linea = br.readLine()
                val todo = StringBuilder()
                while (linea != null){
                    todo.append(linea + "\n")
                    linea = br.readLine()
                }
                br.close()
                archivo.close()
                etPractica15t2.setText(todo)



            }

            catch (ioe: IOException){


            }
        }

        // ------------------ Fin Practica 15


        // ---------------- Practica 16

        btPractica16bt1.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("codigo", etPractica16et1.getText().toString())
            registro.put("descripcion", etPractica16et2.getText().toString())
            registro.put("precio", etPractica16et3.getText().toString())
            bd.insert("articulos", null, registro)
            bd.close()
            etPractica16et1.setText("")
            etPractica16et2.setText("")
            etPractica16et3.setText("")
            Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show()
        }

        btPractica16bt2.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select descripcion,precio from articulos where codigo=${etPractica16et1.text.toString()}", null)
            if (fila.moveToFirst()) {
                etPractica16et2.setText(fila.getString(0))
                etPractica16et3.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un artículo con dicho código",  Toast.LENGTH_SHORT).show()
            bd.close()
        }

        btPractica16bt3.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select codigo,precio from articulos where descripcion='${etPractica16et2.text.toString()}'", null)
            if (fila.moveToFirst()) {
                etPractica16et1.setText(fila.getString(0))
                etPractica16et3.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un artículo con dicha descripción", Toast.LENGTH_SHORT).show()
            bd.close()
        }

        btPractica16bt4.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val cant = bd.delete("articulos", "codigo=${etPractica16et1.text.toString()}", null)
            bd.close()
            etPractica16et1.setText("")
            etPractica16et2.setText("")
            etPractica16et3.setText("")
            if (cant == 1)
                Toast.makeText(this, "Se borró el artículo con dicho código", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe un artículo con dicho código", Toast.LENGTH_SHORT).show()
        }

        btPractica16bt5.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("descripcion", etPractica16et2.text.toString())
            registro.put("precio", etPractica16et3.text.toString())
            val cant = bd.update("articulos", registro, "codigo=${etPractica16et1.text.toString()}", null)
            bd.close()
            if (cant == 1)
                Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "no existe un artículo con el código ingresado", Toast.LENGTH_SHORT).show()
        }



        //-----------------Fin Practica 16




        //------- Practica 25
        btGato.setOnClickListener {

            val mp = MediaPlayer.create(this,R.raw.gato)
            mp.start()

        }
        btLeon.setOnClickListener {
            val mp = MediaPlayer.create(this,R.raw.leon)
            mp.start()
        }


        // --------Fin practica 25
        // ------ Ptractica 26
        mpGlobal = MediaPlayer.create(this, R.raw.kirby)
        var posicion = 0
        btIniciarAudio.setOnClickListener {
            mpGlobal.start()
            if (btNoCircular.text == "No reproducir en forma circular")
                mpGlobal.isLooping = false
            else
                mpGlobal.isLooping = true
        }

        btPausar.setOnClickListener {

            if (mpGlobal.isPlaying()) {
                posicion = mpGlobal.getCurrentPosition()
                mpGlobal.pause()
            }
        }


        btContinuar.setOnClickListener {

            if (mpGlobal.isPlaying() == false) {
                mpGlobal.seekTo(posicion)
                mpGlobal.start()
            }
        }


        btDetener.setOnClickListener {

            mpGlobal.pause()
            posicion = 0
            mpGlobal.seekTo(0)
        }


        btNoCircular.setOnClickListener {

            if (btNoCircular.text  == "No reproducir en forma circular")
                btNoCircular.setText("Reproducir en forma circular")
            else
                btNoCircular.setText("No reproducir en forma circular")
        }

        // ------ Fin de la Practica


        // -- Prctic 25




        //  ----- Fi9n de la practica

        // inicio Prtixa




        // fin practica



        }//end of onCreate

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onDestroy() {
        super.onDestroy()
        mpGlobal.release()
    }




    }
