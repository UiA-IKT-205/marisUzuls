package com.example.piano.data


data class nNoteTimer(val value:String,val delta:Long, val start:Long, val end: Long){


    override fun toString(): String {

        var durationMiliseconds = end-start
        var durationSeconds =  (durationMiliseconds.toDouble())/1000
        var songNowTime = (delta.toDouble())/1000

        return "At song time $songNowTime seconds, $value is pressed for $durationMiliseconds miliseconds, or $durationSeconds seconds"
    }
}

fun trackDurationTimer(start:Long, end: Long): List<Int>{

        var durationMiliseconds = end-start
        var durationSeconds =  (durationMiliseconds.toDouble())/1000
        var durationMinutesForMinutes =  (durationSeconds/60).toInt()
        var durationMinutesForSeconds =  (durationSeconds % 60).toInt()
        val durationList = listOf(durationMinutesForMinutes,durationMinutesForSeconds)
        return durationList
}