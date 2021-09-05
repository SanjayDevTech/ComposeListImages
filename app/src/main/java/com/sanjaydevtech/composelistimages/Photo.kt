package com.sanjaydevtech.composelistimages

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val avg_color: String,
    val src: PhotoSet,
)

data class PhotoSet(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String,
)