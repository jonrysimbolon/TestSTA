package com.teststa.data

object Variable {
    const val DEFAULT_DATE_UI = "00-Jan-00"
    const val DEFAULT_DATE_DB = "0000-00-00"
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
}