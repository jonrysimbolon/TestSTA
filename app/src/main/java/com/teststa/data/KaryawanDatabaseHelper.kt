package com.teststa.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class KaryawanDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "KaryawanDatabase.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TBL)
        listOfKaryawan.forEach { karyawan ->
            insertToKaryawanTbl(
                db,
                karyawan
            )
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema upgrades if needed
    }

    fun insertToKaryawanTbl(db: SQLiteDatabase, karyawan: Karyawan) {
        val values = ContentValues().apply {
            put(ID_KARYAWAN_FIELD, karyawan.idKaryawan)
            put(NM_KARYAWAN_FIELD, karyawan.nmKaryawan)
            put(TGL_MSK_KARYAWAN_FIELD, karyawan.tglMasukKerja)
            put(USIA_KARYAWAN_FIELD, karyawan.usia)
        }
        db.insert(KARYAWAN_TBL, null, values)
    }

    fun editToKaryawanTbl(db: SQLiteDatabase, karyawan: Karyawan) {
        val values = ContentValues().apply {
            put(NM_KARYAWAN_FIELD, karyawan.nmKaryawan)
            put(TGL_MSK_KARYAWAN_FIELD, karyawan.tglMasukKerja)
            put(USIA_KARYAWAN_FIELD, karyawan.usia)
        }
        db.update(KARYAWAN_TBL, values, "$ID_KARYAWAN_FIELD = '${karyawan.idKaryawan}'", null)
    }

    fun deleteFromKaryawanTbl(db: SQLiteDatabase,id: String) {
        db.delete(KARYAWAN_TBL, "$ID_KARYAWAN_FIELD = '$id'", null)
    }

    fun selectBaseOnFromKaryawanTbl(
        db: SQLiteDatabase,
        nmStart: String,
        nmEnd: String,
        usiaStart: String,
        usiaEnd: String,
        tglStart: String,
        tglEnd: String
    ): List<Karyawan> {
        val result = mutableListOf<Karyawan>()
        val selectionBuilder = StringBuilder()
        val selectionArgs = mutableListOf<String>()

        if (nmStart.isNotEmpty() && nmEnd.isNotEmpty()) {
            selectionBuilder.append("$NM_KARYAWAN_FIELD BETWEEN")
            selectionBuilder.append(" ?")
            selectionArgs.add(nmStart)
            selectionBuilder.append(" AND ")
            selectionBuilder.append(" ?")
            selectionArgs.add(nmEnd)

            if (usiaStart.isNotEmpty() && usiaEnd.isNotEmpty()) {
                selectionBuilder.append(" AND ")
            }
        }

        if (usiaStart.isNotEmpty() && usiaEnd.isNotEmpty()) {
            selectionBuilder.append("$USIA_KARYAWAN_FIELD BETWEEN")
            selectionBuilder.append(" ?")
            selectionArgs.add(usiaStart)
            selectionBuilder.append(" AND ")
            selectionBuilder.append(" ?")
            selectionArgs.add(usiaEnd)

            if (tglStart.isNotEmpty() && tglEnd.isNotEmpty()) {
                selectionBuilder.append(" AND ")
            }
        }

        if (tglStart.isNotEmpty() && tglEnd.isNotEmpty()) {
            selectionBuilder.append("$TGL_MSK_KARYAWAN_FIELD BETWEEN")
            selectionBuilder.append(" ?")
            selectionArgs.add(tglStart)
            selectionBuilder.append(" AND ")
            selectionBuilder.append(" ?")
            selectionArgs.add(tglEnd)
        }

        val selection = selectionBuilder.toString()

        val cursor: Cursor? = db.query(
            KARYAWAN_TBL,
            null,
            selection,
            selectionArgs.toTypedArray(),
            null,
            null,
            null
        )

        cursor?.apply {
            val idIndex = getColumnIndex(ID_KARYAWAN_FIELD)
            val nmIndex = getColumnIndex(NM_KARYAWAN_FIELD)
            val tglMskIndex = getColumnIndex(TGL_MSK_KARYAWAN_FIELD)
            val usiaIndex = getColumnIndex(USIA_KARYAWAN_FIELD)

            while (moveToNext()) {
                val id = if (idIndex != -1) getString(idIndex) else null
                val name = if (nmIndex != -1) getString(nmIndex) else null
                val tglMsk = if (tglMskIndex != -1) getString(tglMskIndex) else null
                val usia = if (usiaIndex != -1) getInt(usiaIndex) else null

                val dataModel = Karyawan(id ?: "", name ?: "", tglMsk ?: "", usia ?: 0)
                result.add(dataModel)
            }
            close()
        }
        return result
    }

}
