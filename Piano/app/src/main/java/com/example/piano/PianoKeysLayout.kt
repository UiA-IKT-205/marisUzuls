package com.example.piano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.piano.databinding.FragmentPianoKeysLayoutBinding
import kotlinx.android.synthetic.main.fragment_piano_keys_layout.view.*

class PianoKeysLayout : Fragment() {

    private var _binding: FragmentPianoKeysLayoutBinding? = null
    private val binding get() = _binding!!

    //Keeping the names simple
    //Fulltone
    private val pianoBlackKey = listOf("C#","D#","F#","G#","A#","C2#","D2#","F2#","G2#","A2#")
    //Halftone
    private val pianoWhiteKey = listOf("C","D","E","F","G","A","B","C2","D2","E2","F2","G2","A2","B2")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPianoKeysLayoutBinding.inflate(layoutInflater)
        val view = binding.root

        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()


        pianoWhiteKey.forEach{

            val currentWhitePianoKey = FullToneKeys.newInstance(it)
            currentWhitePianoKey.onWhiteKeyPressDown = {println("Currently pressed note $it")}
            currentWhitePianoKey.onWhiteKeyRelease = {println("Currently pressed note $it")}
            fragmentTransaction.add(view.FullTonePianoKeyLinearLayout.id, currentWhitePianoKey, "note_$it")
        }

        pianoBlackKey.forEach{

            val currentBlackPianoKey = HalfToneKeys.newInstance(it)
            currentBlackPianoKey.onBlackKeyPressDown = {println("Currently pressed note $it")}
            currentBlackPianoKey.onBlackKeyRelease = {println("Currently pressed note $it")}
            fragmentTransaction.add(view.HalfToneKeyLinearLayout.id, currentBlackPianoKey, "note_$it")
        }

        fragmentTransaction.commit()
        return view
    }
}