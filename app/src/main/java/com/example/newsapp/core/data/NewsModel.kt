package com.example.newsapp.core.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    val title: String?,
    val description: String?,
    val author: String?,
    val source: Source?,
    val content: String?,
    val publishedAt: String?,
    val url: String?,
    val urlToImage: String?
): Parcelable

@Parcelize
data class Source(
    val id: String?,
    val name: String?
): Parcelable