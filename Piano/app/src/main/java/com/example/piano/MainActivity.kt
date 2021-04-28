package com.example.piano

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.piano.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private val tag:String = "Piano:MainActivity"
    private lateinit var piano:PianoKeysLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        anonymousSignIn()
        piano = supportFragmentManager.findFragmentById(binding.myPiano.id) as PianoKeysLayout

        piano.onTuneSave = {
            this.tuneFileUpload(it)
        }
    }

    private fun anonymousSignIn(){
        auth.signInAnonymously().addOnSuccessListener {
            Log.d(tag,"LogIn ${it.user.toString()} ok")
        }.addOnFailureListener(){
            Log.d(tag,"LogIn failure")
        }
    }

    private fun tuneFileUpload(file: Uri){

        Log.d(tag, "File uploaded")

        var firebaseStorageRef = FirebaseStorage.getInstance().reference.child("tunes/${file.lastPathSegment}")
        var uploadTune = firebaseStorageRef.putFile(file)

        uploadTune.addOnSuccessListener {
            Log.d(tag, "File ${it.toString()} uploaded successfully")
        }.addOnFailureListener(){
            Log.d(tag, "File upload failure")
        }

    }
}