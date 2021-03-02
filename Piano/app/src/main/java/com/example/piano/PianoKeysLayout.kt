package com.example.piano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.piano.data.nNoteTimer
import com.example.piano.data.trackDurationTimer
import com.example.piano.databinding.FragmentPianoKeysLayoutBinding
import kotlinx.android.synthetic.main.fragment_piano_keys_layout.view.*
import java.io.File
import java.io.FileOutputStream

class PianoKeysLayout : Fragment() {

    private var _binding: FragmentPianoKeysLayoutBinding? = null
    private val binding get() = _binding!!

    //Keeping the names simple
    //Fulltone
    private val pianoBlackKey = listOf("C#", "D#", "E#", "F#", "G#", "A#", "B#", "C2#", "D2#", "E2#", "F2#", "G2#", "A2#")
    //Halftone
    private val pianoWhiteKey = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2", "A2", "B2")


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPianoKeysLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var noteList:MutableList<nNoteTimer> = mutableListOf()
        var songStartTime:Long = 0
        var songEndTime:Long = 0
        var songIsRecording = false
        var durationMinutes:Int =0
        var durationSeconds:Int =0
        var songEndText:String = ""
        var lastClickDelta:Long =0



        pianoWhiteKey.forEach{

            var whiteNotePlaying:Long = 0
            val currentWhitePianoKey = FullToneKeys.newInstance(it)


                currentWhitePianoKey.onWhiteKeyPressDown = {
                    if (songIsRecording){
                        whiteNotePlaying = (System.nanoTime()/1000000) //nanoseconds to milliseconds
                        lastClickDelta = ((whiteNotePlaying-songStartTime))
                        println("Currently pressed note $it")
                    }
                    else{
                        println("Please click start to begin recording")
                    }
                }

                currentWhitePianoKey.onWhiteKeyRelease = {
                    if (songIsRecording){

                        var whiteNoteStopPlaying = (System.nanoTime()/1000000) //nanoseconds to milliseconds
                        val wNote = nNoteTimer(it,lastClickDelta ,whiteNotePlaying, whiteNoteStopPlaying)
                        noteList.add(wNote)
                        println("$wNote")

                    }
                    else{
                    println("Please click start to begin recording")
                    }
                }

                fragmentTransaction.add(view.FullTonePianoKeyLinearLayout.id, currentWhitePianoKey, "note_$it")
        }

        pianoBlackKey.forEach{

            var blackNotePlaying:Long = 0
            val currentBlackPianoKey = HalfToneKeys.newInstance(it)


            currentBlackPianoKey.onBlackKeyPressDown = {
                if (songIsRecording){
                    blackNotePlaying = (System.nanoTime()/1000000) //nanoseconds to milliseconds
                    lastClickDelta = ((blackNotePlaying-songStartTime))
                    println("Currently pressed note $it")
                }
                else{
                    println("Please click start to begin recording")
                }
            }

            currentBlackPianoKey.onBlackKeyRelease = {
                if (songIsRecording) {
                    var blackNoteStopPlaying = (System.nanoTime()/1000000) //nanoseconds to milliseconds
                    val bNote = nNoteTimer(it,lastClickDelta, blackNotePlaying, blackNoteStopPlaying)
                    noteList.add(bNote)
                    println("$bNote")
                }
                else{
                    println("Please click start to begin recording")
                }
            }

            fragmentTransaction.add(view.HalfToneKeyLinearLayout.id, currentBlackPianoKey, "note_$it")

        }

        fragmentTransaction.commit()


        //Start and stop buttons to seperate recordings of different tracks, as well as not immediately recording
        //as you press down one of the piano keys.
        view.startRecording.setOnClickListener {
            songIsRecording = true
            songStartTime = (System.nanoTime()/1000000) //nanoseconds to milliseconds
            println("Recording started")
        }

        view.stopRecording.setOnClickListener {
            songIsRecording = false
            songEndTime = (System.nanoTime()/1000000) //nanoseconds to milliseconds
            val tempList= trackDurationTimer(songStartTime,songEndTime)
            durationMinutes = tempList[0]
            durationSeconds = tempList[1]
            println("Recording stopped")
            songEndText ="Song ended, total duration $durationMinutes minutes and $durationSeconds seconds"
            println(songEndText)
        }


        view.noteFileSaveBtn.setOnClickListener {
            var fileName = view.noteFileNameEditBox.text.toString()
            val path = this.activity?.getExternalFilesDir(null)
            val fileDuplicate:File = File(path,"$fileName.tune")


            if(fileDuplicate.exists()) {
                println("Sorry, there already exists a file with such name, please choose another name!")
            }
            else{
                if (noteList.count() > 0 && fileName.isNotEmpty() && path != null){
                    fileName = "$fileName.tune"
                    FileOutputStream(File(path, fileName), true).bufferedWriter().use { writer ->
                        writer.write("Song $fileName start\n")
                        noteList.forEach {
                            writer.write("${it.toString()}\n")
                        }
                        writer.write(songEndText)
                        noteList = mutableListOf() // new fresh list
                        println("Recording saved, new track can be written")
                        view.noteFileNameEditBox.text.clear()
                    }
                }
            }
        }

        return view
    }
}
