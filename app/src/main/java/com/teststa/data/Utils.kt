package com.teststa.data

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import com.teststa.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

fun String.dateToUiFormatYearFourDigit(): String {

    val locale = Locale("id", "ID")
    val fromFormat = "yyyy-MM-dd"
    val toFormat = "dd-MMM-yyyy"

    return this.dateFormat(
        fromFormat,
        toFormat,
        locale
    )
}

fun alertBelumMemilihData(context: Context) {
    alert(context, context.getString(R.string.silahkan_memilih_data))
}

fun alertEmptyOnFrom(context: Context) {
    alert(context, context.getString(R.string.pastikan_data))
}

fun alert(context: Context, msg: String) {
    Toast.makeText(
        context,
        msg,
        Toast.LENGTH_SHORT
    ).show()
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

fun showDatePickerDialog(
    context: Context,
    onSelectedDate: (
        sqlFormat: String,
        uiFormat: String
    ) -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val formattedMonth = (selectedMonth + 1).toString().padStart(2, '0')
            val sqlFormat = "$selectedYear-$formattedMonth-$selectedDay"
            val uiFormat = sqlFormat.dateToUiFormat()
            onSelectedDate(
                sqlFormat,
                uiFormat
            )
        },
        year,
        month,
        day
    )

    datePickerDialog.show()
}
