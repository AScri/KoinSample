package com.ascri.koinsample.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Cat(
    val id: String = "",
    @JsonProperty("url")
    val imageUrl: String = ""
)