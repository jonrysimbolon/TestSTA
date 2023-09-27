package com.teststa.data

import android.os.Parcelable
import com.teststa.base.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Karyawan(
    val idKaryawan: String,
    val nmKaryawan: String,
    val tglMasukKerja: String,
    val usia: Int,
    override val id: Int = idKaryawan.toIntCustom()
): BaseModel<Int>, Parcelable

fun String.toIntCustom(): Int{
    return try{
        this.toInt()
    }catch (e: Exception){
        0
    }
}