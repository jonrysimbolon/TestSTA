package com.teststa.ui

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.teststa.adapter.KaryawanAdapter
import com.teststa.data.Karyawan
import com.teststa.data.KaryawanDatabaseHelper
import com.teststa.databinding.ActivityMainBinding
import com.teststa.ui.Form2.Companion.MY_DATA

class MainActivity : AppCompatActivity() {

    private var dbHelper: KaryawanDatabaseHelper? = null
    private var db: SQLiteDatabase? = null

    private val karyawanAdapter: KaryawanAdapter by lazy {
        KaryawanAdapter()
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var listKaryawan: List<Karyawan> = emptyList()

    private var selectionData: Karyawan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (dbHelper == null)
            dbHelper = KaryawanDatabaseHelper(this)

        if (db == null)
            db = dbHelper?.writableDatabase


        binding.apply {
            listKaryawanRv.adapter = karyawanAdapter
            listKaryawan = searchResult(dbHelper, db)
            karyawanAdapter.updateData(listKaryawan)

            karyawanAdapter.onClickItem = { _, data ->
                selectionData = data
            }

            searchBtn.setOnClickListener {
                listKaryawan = searchResult(dbHelper, db)
                karyawanAdapter.updateData(listKaryawan)
            }

            closeBtn.setOnClickListener {
                finish()
            }

            newBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, Form2::class.java))
            }

            editBtn.setOnClickListener {
                if(selectionData != null){
                    val intent = Intent(this@MainActivity, Form2::class.java)
                    intent.putExtra(MY_DATA ,selectionData)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@MainActivity, "Silahkan memilih data terlebih dahulu", Toast.LENGTH_SHORT).show()
                }
            }

            deleteBtn.setOnClickListener {
                if(selectionData != null){
                    dbHelper?.deleteFromKaryawanTbl(db!!, selectionData!!.idKaryawan)
                    listKaryawan = listKaryawan.filter { it.idKaryawan != selectionData!!.idKaryawan }
                    karyawanAdapter.updateData(listKaryawan)
                }else{
                    Toast.makeText(this@MainActivity, "Silahkan memilih data terlebih dahulu", Toast.LENGTH_SHORT).show()
                }
            }

            userGithubBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, ListUserGithub::class.java))
            }
        }
    }

    private fun searchResult(
        dbHelper: KaryawanDatabaseHelper?,
        db: SQLiteDatabase?
    ): List<Karyawan> {
        binding.apply {
            val nmStart = nmKaryawanTxtET.text.toString()
            val nmEnd = nmKaryawanEndTxtET.text.toString()
            val usiaStart = usiaTxtET.text.toString()
            val usiaEnd = usiaEndTxtET.text.toString()
            val tglStart = tglMskKrjTxtET.text.toString()
            val tglEnd = tglMskKrjEndTxtET.text.toString()

            return db?.let {
                dbHelper?.selectBaseOnFromKaryawanTbl(
                    it,
                    nmStart,
                    nmEnd,
                    usiaStart,
                    usiaEnd,
                    tglStart,
                    tglEnd
                )
            } ?: emptyList()
        }
    }

    override fun onDestroy() {
        db?.close()
        dbHelper = null
        super.onDestroy()
    }
}