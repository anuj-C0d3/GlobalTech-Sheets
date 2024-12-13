package com.example.globaltechsheets

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField


@Entity(tableName = "JobsheetTable")
 data class Jobsheetdata(
    @PrimaryKey(autoGenerate = false) val requestNo:String,
    val customerName:String,
    val address1:String,
    val address2:String,
    val keypad:Boolean,
    val fingerprint:Boolean,
    val completeAssesor:Boolean,
    val inwarrenty:Boolean,
    val repair:Boolean,
    val service:Boolean,
    val install:Boolean,
    val date:String,
    val productname:String,
    val spareParts:String,
    val faultDes1:String,
    val faultdes2:String,
    val serviceCharge:String,
    val sparePart2:String,
    val expertName:String,
    val signature:Bitmap,
    val customerSign:Bitmap,
    val paymethod:Boolean,
    val mobile:String=""
)
