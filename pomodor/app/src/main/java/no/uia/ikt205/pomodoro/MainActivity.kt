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
var timerOn = false;

class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var startButton:Button

    lateinit var button30:Button
    lateinit var button60:Button
    lateinit var button90:Button
    lateinit var button120:Button

    lateinit var coutdownDisplay:TextView

    var timeToCountDownInMs = 0L
    var minute = 60000L
    val timeTicks = 1000L


    fun checkIfTimerRunning(){

        if (timerOn==true){
            timer.cancel()
            timerOn=false;
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

        timerOn=true
        timer.start()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)

        button30 = findViewById<Button>(R.id.button30Min)
        button60 = findViewById<Button>(R.id.button60Min)
        button90 = findViewById<Button>(R.id.button90Min)
        button120 = findViewById<Button>(R.id.button120Min)

        button30.setOnClickListener(this)
        button60.setOnClickListener(this)
        button90.setOnClickListener(this)
        button120.setOnClickListener(this)

        startButton.setOnClickListener(){
            startCountDown(it)
        }

        coutdownDisplay = findViewById<TextView>(R.id.countDownView)
    }

    override fun onClick(view:View) {
        checkIfTimerRunning()
        when (view.id) {
            R.id.button30Min -> {
                timeToCountDownInMs = 30*minute
            }
            R.id.button60Min -> {
                timeToCountDownInMs = 60*minute
            }
            R.id.button90Min -> {
                timeToCountDownInMs = 90*minute
            }
            R.id.button120Min -> {
                timeToCountDownInMs = 120*minute
            }
        }
        updateCountDownDisplay(timeToCountDownInMs)
    }



    fun updateCountDownDisplay(timeInMs: Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}