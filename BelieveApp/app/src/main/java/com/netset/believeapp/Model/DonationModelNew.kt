package com.netset.believeapp.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DonationModelNew(
    val `data`: Data?,
    val message: String?,
    val status: Int?
) : Parcelable

@Parcelize
data class Giving(
    val _id: String?,
    val giving_url: String?,
    val updatedAt: String?
) : Parcelable

@Parcelize
data class Data(
    val giving: List<Giving>?,
    val teaching: List<Teaching>?
) : Parcelable

@Parcelize
data class Teaching(
    val __v: Int?,
    val _id: String?,
    val blog: String?,
    val blog_image: String?,
    val blog_title: String?,
    val category: String?,
    val createdAt: String?,
    val status: String?,
    val time_ago: String?,
    val updatedAt: String?
) : Parcelable