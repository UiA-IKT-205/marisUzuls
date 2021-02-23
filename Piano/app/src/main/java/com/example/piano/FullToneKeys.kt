package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.piano.databinding.FragmentFullToneKeysBinding
import kotlinx.android.synthetic.main.fragment_full_tone_keys.view.*


class FullToneKeys : Fragment() {

    private var _binding:FragmentFullToneKeysBinding? = null
    private val binding get() = _binding!!
    private lateinit var note:String

    var onWhiteKeyPressDown:((note:String) -> Unit)? = null
    var onWhiteKeyRelease:((note:String) -> Unit)? = null

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

        _binding = FragmentFullToneKeysBinding.inflate(layoutInflater)
        val view = binding.root

        view.FullTonePianoKeyBtn.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean{
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> this@FullToneKeys.onWhiteKeyPressDown?.invoke(note)
                    MotionEvent.ACTION_UP -> this@FullToneKeys.onWhiteKeyRelease?.invoke(note)
                }
                return true
            }
        })
        return view
    }

    companion object {
        fun newInstance(note:String) =
            FullToneKeys().apply {
                arguments = Bundle().apply {
                    putString("NOTE", note)
                }
            }
    }
}