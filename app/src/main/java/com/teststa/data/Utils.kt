package com.teststa.data

const val KARYAWAN_TBL = "Karyawan"
const val ID_KARYAWAN_FIELD = "IDKaryawan"
const val NM_KARYAWAN_FIELD = "NmKaryawan"
const val TGL_MSK_KARYAWAN_FIELD = "TglMasukKerja"
const val USIA_KARYAWAN_FIELD = "Usia"

val CREATE_TBL = """
    CREATE TABLE $KARYAWAN_TBL (
        $ID_KARYAWAN_FIELD TEXT(30) PRIMARY KEY,
        $NM_KARYAWAN_FIELD TEXT(30),
        $TGL_MSK_KARYAWAN_FIELD DATETIME,
        $USIA_KARYAWAN_FIELD INTEGER
    );
""".trimIndent()

val listOfKaryawan = listOf(
    Karyawan(
        "001",
        "Andi",
        "2-Mar-12",
        25
    ),
    Karyawan(
        "002",
        "Anto",
        "2-Jun-13",
        24
    ),
    Karyawan(
        "003",
        "Adi",
        "2-May-00",
        27
    ),
    Karyawan(
        "004",
        "Amin",
        "5-Aug-18",
        31
    ),
)
