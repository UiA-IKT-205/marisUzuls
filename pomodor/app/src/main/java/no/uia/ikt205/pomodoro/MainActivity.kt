package no.uia.ikt205.pomodoro

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

lateinit var workTimer: CountDownTimer
lateinit var pauseTimer: CountDownTimer


var timerWorkOn = false;
var timerPauseOn = false;
var timerRepetitionsCounter = 0

class MainActivity : AppCompatActivity(), View.OnClickListener {


    // Variable initiation
    lateinit var repetitions:TextView
    lateinit var mainActivityPomodorTitle:TextView
    lateinit var startButton:Button
    lateinit var stopButton:Button

    lateinit var mainPagePomodoroSeekBarWorkTime: SeekBar
    lateinit var mainPagePomodoroSeekBarPauseTime: SeekBar

    lateinit var mainPagePomodoroSeekBarWorkTimeValueRepresent:TextView
    lateinit var mainPagePomodoroSeekBarPauseTimeValueRepresent:TextView

    lateinit var coutdownDisplay:TextView
    lateinit var timerRepetionTextBox:EditText

    var timeToCountDownInMs = 0L
    var timePauseToCountDownInMs = 0L
    var minute = 60000L
    val timeTicks = 1000L


    // Simple function to disable timer if already running
    fun checkIfTimerRunning(){

        if (timerWorkOn==true){
            workTimer.cancel()
            timerWorkOn=false;
        }
        if (timerPauseOn==true){
            pauseTimer.cancel()
            timerPauseOn=false;
        }
    }


    // Definition for general work timer
    // Fetch view and data from timeToCountDownInMs to initiate timer
    // When timer for work period is done, it launches a timer for pause period.

    fun startCountDown(v: View){


        workTimer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                startCountDownPause(v)
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }

        }

        repetitions.text = timerRepetitionsCounter.toString()
        mainActivityPomodorTitle.text= "Working"
        timerWorkOn=true
        workTimer.start()

    }


    // Definition for pause timer
    // Gets values from timePauseToCountDownInMs as a specified pause duration,
    // And from timerRepetitionsCounter which states how many times timer will repeat it self
    // If amount of specified repetitions is not set to 0, onfinish initiates another work timer
    // If repetitions (timerRepetitionsCounter) are currently 0, timer stops with done message.

    fun startCountDownPause(v: View){

        pauseTimer = object : CountDownTimer(timePauseToCountDownInMs,timeTicks) {
            override fun onFinish() {
                if (timerRepetitionsCounter != 0){
                    timerRepetitionsCounter -= 1
                    repetitions.text = timerRepetitionsCounter.toString()
                    startCountDown(v)
                }
                else{
                    mainActivityPomodorTitle.text= "Done"
                }

            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }

        mainActivityPomodorTitle.text= "Pause"
        timerPauseOn=true
        pauseTimer.start()

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //Fetching variables by their ids

        repetitions = findViewById<TextView>(R.id.valueRepetitions)

        mainActivityPomodorTitle = findViewById<TextView>(R.id.currentActivityTitle)
        startButton = findViewById<Button>(R.id.startCountdownButton)
        stopButton = findViewById<Button>(R.id.stopCountdownButton)

        mainPagePomodoroSeekBarWorkTimeValueRepresent = findViewById<TextView>(R.id.seekBarCurrentWorkTimeSelected)
        mainPagePomodoroSeekBarWorkTime = findViewById<SeekBar>(R.id.pomodoroWorkSeekBar)

        mainPagePomodoroSeekBarPauseTimeValueRepresent = findViewById<TextView>(R.id.seekBarCurrentPauseTimeSelected)
        mainPagePomodoroSeekBarPauseTime = findViewById<SeekBar>(R.id.pomodoroPauseSeekBar)

        timerRepetionTextBox = findViewById<EditText>(R.id.pomodoroTimerRepetitionInput)

        coutdownDisplay = findViewById<TextView>(R.id.countDownView)

        stopButton.visibility = View.GONE




        // Define intractable seekBar for changing values of work timer. (timeToCountDownInMs)

        mainPagePomodoroSeekBarWorkTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                mainPagePomodoroSeekBarWorkTimeValueRepresent.text = "$i minutes"
                timeToCountDownInMs = i * minute
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }

        })



        // Define intractable seekBar for changing values of pause timer. (timePauseToCountDownInMs)

        mainPagePomodoroSeekBarPauseTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                mainPagePomodoroSeekBarPauseTimeValueRepresent.text = "$i minutes"
                timePauseToCountDownInMs = i * minute
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }

        })


        // Define for editText object
        // Some error management on onTextChanged involves, handling case where deleting the last
        // number in input box resolves in crash (parsed string is null)

        timerRepetionTextBox.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                var input  = timerRepetionTextBox.text.toString();

                if (input != ""){
                    timerRepetitionsCounter = Integer.parseInt(input);
                }

            }
        })


        // Defined start button, with a boolean checker for other timers running.
        // If other timer is running cancels it
        startButton.setOnClickListener(){
            checkIfTimerRunning()
            stopButton.visibility = View.VISIBLE
            startButton.visibility = View.GONE

            // added to make it possible to rerun timer without changing editText again
            var input  = timerRepetionTextBox.text.toString();
            if (input != ""){
                timerRepetitionsCounter = Integer.parseInt(input);
                repetitions.text = input
            }
            startCountDown(it)
        }

        stopButton.setOnClickListener(){
            checkIfTimerRunning()
            stopButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
            repetitions.text = ""
            timerRepetitionsCounter = 0
            coutdownDisplay.text = millisecondsToDescriptiveTime(0)
            mainActivityPomodorTitle.text= "Stopped"

        }


    }

    override fun onClick(view:View) {
        updateCountDownDisplay(timeToCountDownInMs)
    }


    fun updateCountDownDisplay(timeInMs: Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}