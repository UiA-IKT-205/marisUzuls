package no.uia.ikt205.tictactoemu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import no.uia.ikt205.tictactoemu.databinding.ActivityMainBinding
import no.uia.ikt205.tictactoemu.gameStates.DialogsForGameInterface
import no.uia.ikt205.tictactoemu.gameStates.GameInstanceCreate
import no.uia.ikt205.tictactoemu.gameStates.GameInstanceJoin


class MainActivity : AppCompatActivity(), DialogsForGameInterface {

    val TAG:String = "MainActivity logging"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startGameButton.setOnClickListener {
            createNewGame()
        }
        binding.joinGameButton.setOnClickListener {
            joinGame()
        }

    }

    private fun createNewGame(){
        val newGameCreatePrompt = GameInstanceCreate()
        newGameCreatePrompt.show(supportFragmentManager,"CreateGameDialogFragment")
    }

    private fun joinGame(){
        val gameJoinPrompt = GameInstanceJoin()
        gameJoinPrompt.show(supportFragmentManager,"JoinGameInstanceFragment")
    }

    override fun onDialogCreateGame(playerSpecifiedName: String) {
        Log.d(TAG,playerSpecifiedName)
        GameManager.createGame(playerSpecifiedName)
    }

    override fun onDialogJoinGame(playerSpecifiedName: String, activeGameId: String) {
        Log.d(TAG, "$playerSpecifiedName $activeGameId")
        GameManager.joinGame(playerSpecifiedName,activeGameId)
    }

}