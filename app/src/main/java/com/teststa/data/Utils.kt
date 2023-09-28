package com.teststa.data

import java.text.SimpleDateFormat
import java.util.Locale

const val KARYAWAN_TBL = "Karyawan"
const val ID_KARYAWAN_FIELD = "IDKaryawan"
const val NM_KARYAWAN_FIELD = "NmKaryawan"
const val TGL_MSK_KARYAWAN_FIELD = "TglMasukKerja"
const val USIA_KARYAWAN_FIELD = "Usia"

val CREATE_TBL = """
    CREATE TABLE $KARYAWAN_TBL (
        $ID_KARYAWAN_FIELD TEXT(30) PRIMARY KEY,
        $NM_KARYAWAN_FIELD TEXT(30),
        $TGL_MSK_KARYAWAN_FIELD DATE,
        $USIA_KARYAWAN_FIELD INTEGER
    );
""".trimIndent()

fun String.dateToUiFormat(): String {

    val locale = Locale("id", "ID")
    val fromFormat = "yyyy-MM-dd"
    val toFormat = "dd-MMM-yy"

    return this.dateFormat(
        fromFormat,
        toFormat,
        locale
    )
}

fun String.dateToSqlFormat(): String {

    val locale = Locale("id", "ID")
    val fromFormat = "dd-MMM-yy"
    val toFormat = "yyyy-MM-dd"

    return this.dateFormat(
        fromFormat,
        toFormat,
        locale
    )
}

fun String.dateFormat(fromFormat: String, toFormat: String, locale: Locale): String {
    return try {
        val inputFormat = SimpleDateFormat(fromFormat, locale)
        val parsedDate = inputFormat.parse(this)

        val outputFormat = SimpleDateFormat(toFormat, locale)
        outputFormat.format(parsedDate ?: this)
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}

val listOfKaryawan = listOf(
    Karyawan(
        "001",
        "Andi",
        "2012-03-02",
        25
    ),
    Karyawan(
        "002",
        "Anto",
        "2013-06-02",
        24
    ),
    Karyawan(
        "003",
        "Adi",
        "2000-05-02",
        27
    ),
    Karyawan(
        "004",
        "Amin",
        "2018-08-05",
        31
    ),
)
