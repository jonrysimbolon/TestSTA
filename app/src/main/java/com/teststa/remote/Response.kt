package com.teststa.remote

import com.google.gson.annotations.SerializedName
import com.teststa.base.BaseModel

data class Repo(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("html_url")
    val htmlUrl: String,
): BaseModel<Int>