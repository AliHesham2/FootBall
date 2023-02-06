package com.example.football.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.football.R
import com.example.football.model.api.ScoreObject
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object{
        @SuppressLint("SimpleDateFormat")
        var simpleFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        @SuppressLint("SimpleDateFormat")
        var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        @SuppressLint("SimpleDateFormat")
        var simpleHourFormat: SimpleDateFormat = SimpleDateFormat("HH:mm")
        const val BASE_URL = "https://api.football-data.org"
        private var cal: Calendar = Calendar.getInstance()


        fun alertMsg(view: View, msg:String? = null , id : Int ? = null ){
            Snackbar.make(view, msg ?: view.context.getString(id!!), Snackbar.LENGTH_LONG).also { snackBar ->
                snackBar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.red))
            }.show()
        }

        fun toastMsg(context: Context, msg:String? = null , id : Int ? = null ){
            Toast.makeText(context, msg ?: context.getString(id!!), Toast.LENGTH_SHORT).show()
        }


        /* get day as `Sunday`  */
        fun getDayNameOnly(date:String):String{
            cal.time = simpleFormat.parse(date)!!
            return cal.getDisplayName(Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.getDefault())!!
        }

        fun getWeekNumOnly(date:String):Int{
            cal.time = simpleFormat.parse(date)!!
            return cal.get(Calendar.WEEK_OF_YEAR)
        }

        /* get date as `7 jun 2020`  */
        fun getFullDateOnly(date:String):String{
            cal.time = simpleFormat.parse(date)!!
            return DateFormat.getDateInstance().format(cal.time)
        }

        /* get date as `2020-08-15`  */
        fun getDateOnly(date:String):String{
            cal.time = simpleFormat.parse(date)!!
            return  simpleDateFormat.format(cal.time)
        }

        /* get time as `12:00`  */
        fun getTimeOnly(date:String):String{
            cal.time = simpleFormat.parse(date)!!
            return simpleHourFormat.format(cal.time)
        }


        fun getScoreString(scoreObject: ScoreObject): String {
            return if (scoreObject.fullTime.homeTeam != null){
                "${scoreObject.fullTime.homeTeam} - ${scoreObject.fullTime.awayTeam} "
            }else if (scoreObject.halfTime.homeTeam != null){
                "${scoreObject.halfTime.homeTeam} - ${scoreObject.halfTime.awayTeam} "
            }else if (scoreObject.extraTime.homeTeam != null){
                "${scoreObject.extraTime.homeTeam} - ${scoreObject.extraTime.awayTeam} "
            }else{
                "${scoreObject.penalties.homeTeam ?: 0} - ${scoreObject.penalties.awayTeam ?: 0} "
            }
        }

        fun checkDate(value:String,context: Context): String? {
            val currentCal = Calendar.getInstance()
            return if ( getDateOnly(value) == simpleDateFormat.format(currentCal.time) ) {
                 context.getString(R.string.TODAY)
            } else if (simpleDateFormat.format(currentCal.apply { this.set(Calendar.DAY_OF_MONTH,1) }.time) == getDateOnly(value)){
                 context.getString(R.string.TMW)
            }else{
                return null
            }

        }

        /* get Multi data with callBack  */
        fun parseDateString(date:String, callBack : (day:String,weekNumber:Int,fullDate:String,time:String,date:String) ->Unit){
            cal.time = simpleFormat.parse(date)!!
            val day = cal.getDisplayName(Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.getDefault())!!
            val weekNum  = cal.get(Calendar.WEEK_OF_YEAR)
            val fullDate =  DateFormat.getDateInstance().format(cal.time)
            val time = simpleHourFormat.format(cal.time)
            val dateC = simpleDateFormat.format(cal.time)
            callBack(day,weekNum,fullDate,time,dateC)
        }

    }
}