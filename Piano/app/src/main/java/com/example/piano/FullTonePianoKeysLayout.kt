package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class FullTonePianoKeysLayout : Fragment() {

    private lateinit var note:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getString("Key") ?: "?"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_tone_piano_keys_layout, container, false)
    }

    companion object {
        fun newInstance(note: String) =
            FullTonePianoKeysLayout().apply {
                arguments = Bundle().apply {
                    putString("Key", note)
                }
            }
    }
}