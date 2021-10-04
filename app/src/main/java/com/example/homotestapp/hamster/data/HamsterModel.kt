package com.example.homotestapp.hamster.data

data class HamsterModel(
    val title: String? = "",
    val description: String? = " ",
    var pinned: Boolean? = false,
    val image: String? = ""
)
