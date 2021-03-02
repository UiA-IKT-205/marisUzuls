package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.piano.databinding.FragmentHalfToneKeysBinding
import kotlinx.android.synthetic.main.fragment_half_tone_keys.view.*


class HalfToneKeys : Fragment() {

    private var _binding:FragmentHalfToneKeysBinding? = null
    private val binding get() = _binding!!
    private lateinit var note:String

    var onBlackKeyPressDown:((note:String) -> Unit)? = null
    var onBlackKeyRelease:((note:String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getString("NOTE") ?: "?"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHalfToneKeysBinding.inflate(layoutInflater)
        val view = binding.root
        view.HalfTonePianoKeyBtn.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean{
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> this@HalfToneKeys.onBlackKeyPressDown?.invoke(note)
                    MotionEvent.ACTION_UP -> this@HalfToneKeys.onBlackKeyRelease?.invoke(note)
                }
                return true
            }
        })
        return view
    }

    companion object {
        fun newInstance(note:String) =
            HalfToneKeys().apply {
                arguments = Bundle().apply {
                    putString("NOTE", note)
                }
            }
    }
}