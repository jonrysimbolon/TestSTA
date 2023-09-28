package com.teststa.data

import org.junit.Assert
import org.junit.Test

class UtilsKtTest {

    @Test
    fun dateToUiFormat(){
        val date = "2012-03-02"
        val expect = "02-Mar-12"
        val convertDate = date.dateToUiFormat()
        Assert.assertEquals(expect, convertDate)
    }

    @Test
    fun dateToSqlFormat() {
        val date = "02-Mar-12"
        val expect = "2012-03-02"
        val convertDate = date.dateToSqlFormat()
        Assert.assertEquals(expect, convertDate)
    }
}