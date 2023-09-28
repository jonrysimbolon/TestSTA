package com.teststa.ui

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.teststa.adapter.KaryawanAdapter
import com.teststa.data.Karyawan
import com.teststa.data.KaryawanDatabaseHelper
import com.teststa.data.Variable.DEFAULT_DATE_DB
import com.teststa.data.Variable.DEFAULT_DATE_UI
import com.teststa.data.alertBelumMemilihData
import com.teststa.data.showDatePickerDialog
import com.teststa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

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
    private var tglStartFormatUi: String = DEFAULT_DATE_UI
    private var tglStartFormatDb: String = DEFAULT_DATE_DB
    private var tglEndFormatUi: String = DEFAULT_DATE_UI
    private var tglEndFormatDb: String = DEFAULT_DATE_DB

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

            tglMskKrjTxtET.setOnClickListener(this@MainActivity)
            tglMskKrjEndTxtET.setOnClickListener(this@MainActivity)
            searchBtn.setOnClickListener(this@MainActivity)
            closeBtn.setOnClickListener(this@MainActivity)
            newBtn.setOnClickListener(this@MainActivity)
            editBtn.setOnClickListener(this@MainActivity)
            deleteBtn.setOnClickListener(this@MainActivity)
            userGithubBtn.setOnClickListener(this@MainActivity)
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

            return db?.let {
                dbHelper?.selectBaseOnFromKaryawanTbl(
                    it,
                    nmStart,
                    nmEnd,
                    usiaStart,
                    usiaEnd,
                    tglStartFormatDb,
                    tglEndFormatDb
                )
            } ?: emptyList()
        }
    }

    override fun onDestroy() {
        db?.close()
        dbHelper = null
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        binding.apply {
            when (v) {
                closeBtn -> {
                    finish()
                }

                tglMskKrjTxtET -> {
                    showDatePickerDialog(this@MainActivity) { sqlFormat, uiFormat ->
                        tglStartFormatDb = sqlFormat
                        tglStartFormatUi = uiFormat
                        tglMskKrjTxtET.setText(tglStartFormatUi)
                    }
                }

                tglMskKrjEndTxtET -> {
                    showDatePickerDialog(this@MainActivity){ sqlFormat, uiFormat ->
                        tglEndFormatDb = sqlFormat
                        tglEndFormatUi = uiFormat
                        tglMskKrjEndTxtET.setText(tglEndFormatUi)
                    }
                }

                searchBtn -> {
                    listKaryawan = searchResult(dbHelper, db)
                    karyawanAdapter.updateData(listKaryawan)
                }

                newBtn -> {
                    startActivity(Intent(this@MainActivity, Form2Activity::class.java))
                }

                editBtn -> {
                    if (selectionData != null) {
                        Intent(this@MainActivity, Form2Activity::class.java)
                            .apply {
                                putExtra(Form2Activity.MY_DATA, selectionData)
                            }.also {
                                startActivity(it)
                            }
                    } else {
                        alertBelumMemilihData(this@MainActivity)
                    }
                }

                deleteBtn -> {
                    if (selectionData != null) {
                        dbHelper?.deleteFromKaryawanTbl(db!!, selectionData!!.idKaryawan)
                        listKaryawan =
                            listKaryawan.filter { it.idKaryawan != selectionData!!.idKaryawan }
                        karyawanAdapter.updateData(listKaryawan)
                    } else {
                        alertBelumMemilihData(this@MainActivity)
                    }
                }

                userGithubBtn -> {
                    startActivity(Intent(this@MainActivity, ListUserGithubActivity::class.java))
                }
            }
        }
    }
}