package com.dicoding.asclepius.remote.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class History (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "result")
    var result: String = "",

    @ColumnInfo(name = "imageUri")
    var imageUri: String

): Parcelable