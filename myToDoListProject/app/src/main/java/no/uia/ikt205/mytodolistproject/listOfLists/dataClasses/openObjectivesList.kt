package no.uia.ikt205.mytodolistproject.listOfLists.dataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


    @Parcelize
    data class openObjectivesList (val objective:String, var status:Boolean = false): Parcelable

