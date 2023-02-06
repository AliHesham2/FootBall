package com.example.football.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.football.R
import com.example.football.model.api.FootBallApiDetailsObject
import com.example.football.model.api.FootBallStatus
import com.example.football.model.api.ScoreObject
import com.example.football.view.matches.MatchesAdapter


@BindingAdapter("matchesAdapter")
fun matchesAdapter(recyclerView: RecyclerView, data: List<FootBallApiDetailsObject>?) {
    val adapter = recyclerView.adapter as MatchesAdapter
    adapter.submitList(data)
}

@BindingAdapter("matchesDay")
fun matchesDay(textView: TextView, value :String?) {
    value?.let { textView.text = Util.getDayNameOnly(it) }
}

@BindingAdapter("matchesFullDate")
fun matchesFullDate(textView: TextView, value :String?) {
    value?.let { textView.text = Util.getFullDateOnly(it) }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("matchesWeekNumber")
fun matchesWeekNumber(textView: TextView, value :String?) {
    value?.let {  textView.text = textView.context.getString(R.string.Week) +" "+ Util.getWeekNumOnly(it).toString() }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("matchesTime")
fun matchesTime(textView: TextView, value :FootBallApiDetailsObject?) {
    var prefix:String
    value?.let {
        prefix =
            if (it.status == FootBallStatus.FINISHED.name ) { textView.context.getString(R.string.Stared_At) }
            else { textView.context.getString(R.string.Star_At) }
        textView.text = "$prefix  ${Util.getTimeOnly(it.utcDate)}"
    }
}

@BindingAdapter("scoreText")
fun scoreText(textView: TextView, value :ScoreObject?) {
    value?.let { textView.text = Util.getScoreString(it)}
}

@BindingAdapter("checkDate")
fun checkDate(textView: TextView, value :String?) {
       value?.let {textView.text = Util.checkDate(it,textView.context)?:Util.getDateOnly(it)  }
}

@BindingAdapter("imageTint")
fun imageTint(imageView: ImageView, value :String?) {
    var color :Int
    value?.let {
    color = when(it){
        FootBallStatus.POSTPONED.name -> {
            ContextCompat.getColor(imageView.context, R.color.red_1)
        }
        FootBallStatus.LIVE_NOW.name -> {
            ContextCompat.getColor(imageView.context, R.color.red)
        }
        FootBallStatus.SCHEDULED.name -> {
            ContextCompat.getColor(imageView.context, R.color.red_2)
        }
        FootBallStatus.FINISHED.name -> {
            ContextCompat.getColor(imageView.context, R.color.red_3)
        }
        else ->{
            ContextCompat.getColor(imageView.context, R.color.red_3)
        }
    }
        imageView.setColorFilter(color)
    }
}

@BindingAdapter("statusText")
fun statusText(textView: TextView, value :String?) {
    value?.let {
        textView.text = when(it){
            FootBallStatus.POSTPONED.name -> {
                FootBallStatus.POSTPONED.name
            }
            FootBallStatus.LIVE_NOW.name -> {
                FootBallStatus.LIVE_NOW.name
            }
            FootBallStatus.SCHEDULED.name -> {
                FootBallStatus.SCHEDULED.name
            }
            FootBallStatus.FINISHED.name -> {
                FootBallStatus.FINISHED.name
            }
            else ->{
                FootBallStatus.FINISHED.name
            }
        }
    }
}









