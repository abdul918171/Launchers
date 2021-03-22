package com.spacex.api.model

import com.google.gson.annotations.SerializedName

data class Launcher(val id: String?, val name: String?, val date_local: String?, val details: String?,
                    @SerializedName("links") var links: Links)