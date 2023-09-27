package com.teststa.ui

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.teststa.data.Karyawan
import com.teststa.data.KaryawanDatabaseHelper
import com.teststa.databinding.ActivityForm2Binding

class Form2 : AppCompatActivity() {

    companion object{
        const val MY_DATA = "my data"
    }

    private var dbHelper: KaryawanDatabaseHelper? = null
    private var db: SQLiteDatabase? = null

    private var selectionData: Karyawan? = null

    private val binding: ActivityForm2Binding by lazy {
        ActivityForm2Binding.inflate(layoutInflater)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (dbHelper == null)
            dbHelper = KaryawanDatabaseHelper(this)

        if (db == null)
            db = dbHelper?.writableDatabase

        if(intent.extras != null){
            selectionData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(MY_DATA, Karyawan::class.java)
            } else {
                intent.getParcelableExtra(MY_DATA)
            }
            binding.apply {
                saveBtn.text = "Update"
                idEt.setText(selectionData?.idKaryawan)
                idEt.isEnabled = false
                nmEt.setText(selectionData?.nmKaryawan)
                tglEt.setText(selectionData?.tglMasukKerja)
                usiaEt.setText(selectionData?.usia.toString())
            }
        }else{
            binding.apply {
                saveBtn.text = "Save"
            }
        }

        binding.apply {
            saveBtn.setOnClickListener {
                val id = idEt.text.toString()
                val name = nmEt.text.toString()
                val tglMsk = tglEt.text.toString()
                val usia = usiaEt.text.toString()

                if (id.isNotEmpty() && name.isNotEmpty() && tglMsk.isNotEmpty() && usia.isNotEmpty()) {
                    if(selectionData != null){
                        db?.let { it1 ->
                            dbHelper?.editToKaryawanTbl(
                                it1,
                                Karyawan(
                                    id,name,tglMsk,usia.toInt()
                                )
                            )
                        }
                    }else{
                        db?.let { it1 ->
                            dbHelper?.insertToKaryawanTbl(
                                it1,
                                Karyawan(
                                    id,name,tglMsk,usia.toInt()
                                )
                            )
                        }
                    }

                    val intent = Intent(this@Form2, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Form2, "Pastikan tidak ada yang kosong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    override fun onDestroy() {
        db?.close()
        dbHelper = null
        super.onDestroy()
    }
}