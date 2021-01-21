package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

lateinit var timer: CountDownTimer
var timerOn = 0;

class MainActivity : AppCompatActivity() {


    lateinit var startButton:Button

    lateinit var b1:Button
    lateinit var b2:Button
    lateinit var b3:Button
    lateinit var b4:Button

    lateinit var coutdownDisplay:TextView

    var timeToCountDownInMs = 0L
    val timeTicks = 1000L


    fun checker(){

        if (timerOn==1){
            timer.cancel()
            timerOn=0;
        }
    }


    fun startCountDown(v: View){


        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
            }

            override fun onTick(millisUntilFinished: Long) {

                updateCountDownDisplay(millisUntilFinished)
            }

        }

        timerOn=1
        timer.start()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)

        b1 = findViewById<Button>(R.id.time1)
        b2 = findViewById<Button>(R.id.time2)
        b3 = findViewById<Button>(R.id.time3)
        b4 = findViewById<Button>(R.id.time4)

        b1.setOnClickListener(){
            checker()
            timeToCountDownInMs = 1800000L
            updateCountDownDisplay(timeToCountDownInMs)
        }
        b2.setOnClickListener(){
            checker()
            timeToCountDownInMs = 3600000L
            updateCountDownDisplay(timeToCountDownInMs)
        }
        b3.setOnClickListener(){
            checker()
            timeToCountDownInMs = 5400000L
            updateCountDownDisplay(timeToCountDownInMs)
        }
        b4.setOnClickListener(){
            checker()
            timeToCountDownInMs = 7200000L
            updateCountDownDisplay(timeToCountDownInMs)
        }

       startButton.setOnClickListener(){

           startCountDown(it)
       }

       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }




    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}