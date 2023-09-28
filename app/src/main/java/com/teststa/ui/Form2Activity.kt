package com.teststa.ui

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.teststa.R
import com.teststa.data.Karyawan
import com.teststa.data.KaryawanDatabaseHelper
import com.teststa.data.Variable.DEFAULT_DATE_DB
import com.teststa.data.Variable.DEFAULT_DATE_UI
import com.teststa.data.alertEmptyOnFrom
import com.teststa.data.dateToUiFormatYearFourDigit
import com.teststa.data.showDatePickerDialog
import com.teststa.databinding.ActivityForm2Binding

class Form2Activity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val MY_DATA = "my data"
    }

    private var dbHelper: KaryawanDatabaseHelper? = null
    private var db: SQLiteDatabase? = null

    private var selectionData: Karyawan? = null

    private var tglFormatUi: String = DEFAULT_DATE_UI
    private var tglFormatDb: String = DEFAULT_DATE_DB

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

        if (intent.extras != null) {
            selectionData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(MY_DATA, Karyawan::class.java)
            } else {
                intent.getParcelableExtra(MY_DATA)
            }
            binding.apply {
                saveBtn.text = getString(R.string.update)
                idEt.setText(selectionData?.idKaryawan)
                idEt.isEnabled = false
                nmEt.setText(selectionData?.nmKaryawan)
                val tglMsk = selectionData?.tglMasukKerja
                tglFormatDb = tglMsk ?: tglFormatDb
                tglFormatUi = tglMsk?.dateToUiFormatYearFourDigit() ?: tglFormatUi
                tglEt.setText(tglFormatUi)
                usiaEt.setText(selectionData?.usia.toString())
            }
        } else {
            binding.apply {
                saveBtn.text = getString(R.string.save)
            }
        }

        binding.apply {
            tglEt.setOnClickListener(this@Form2Activity)
            saveBtn.setOnClickListener(this@Form2Activity)
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
                tglEt -> {
                    showDatePickerDialog(this@Form2Activity) { sqlFormat, uiFormat ->
                        tglFormatDb = sqlFormat
                        tglFormatUi = uiFormat
                        tglEt.setText(tglFormatUi)
                    }
                }
                saveBtn -> {
                    val id = idEt.text.toString()
                    val name = nmEt.text.toString()
                    val tglMsk = tglEt.text.toString()
                    val usia = usiaEt.text.toString()

                    if (id.isNotEmpty() && name.isNotEmpty() && tglMsk.isNotEmpty() && usia.isNotEmpty()) {

                        db?.let { it1 ->
                            dbHelper?.insertUpdateToKaryawanTbl(
                                it1,
                                Karyawan(
                                    id, name, tglFormatDb, usia.toInt()
                                ),
                                selectionData != null
                            )
                        }

                        Intent(this@Form2Activity, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        }.also {
                            startActivity(it)
                        }
                    } else {
                        alertEmptyOnFrom(this@Form2Activity)
                    }
                }
            }
        }
    }
}