package no.uia.ikt205.tictactoemu

import android.content.Intent
import android.util.Log
import no.uia.ikt205.tictactoemu.ApplicationInterface.Companion.context
import no.uia.ikt205.tictactoemu.api.GameService
import no.uia.ikt205.tictactoemu.api.data.Game
import no.uia.ikt205.tictactoemu.api.data.GameState

typealias GameCallback = (game:Game?) -> Unit

object GameManager {
    private const val TAG:String = "Log for GameManager"
    val StartingGameState:GameState = listOf(mutableListOf('0','0','0'),mutableListOf('0','0','0'),mutableListOf('0','0','0'))

    fun createGame(playerSpecifiedName:String){
        GameService.createGame(playerSpecifiedName,StartingGameState) { game: Game?, err: Int? ->
            if(err != null){
                Log.d(TAG, "Error starting game. Error code : $err")
            } else {
                val intent = Intent(context, GameActivityRemake::class.java)
                intent.putExtra("game", game)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                context.startActivity(intent)
            }
        }
    }

    fun joinGame(playerSpecifiedName:String, activeGameId:String){
        GameService.joinGame(playerSpecifiedName, activeGameId) { game: Game?, err: Int? ->
            if(err != null){
                Log.d(TAG, "Error joining game. Error code : $err")
            } else {
                val intent = Intent(context, GameActivityRemake::class.java)
                intent.putExtra("game", game)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                context.startActivity(intent)
            }
        }
    }

    fun pollGame(activeGameId:String, callback:GameCallback){
        GameService.pollGame(activeGameId) { game: Game?, err: Int? ->
            if(err != null){
                Log.d(TAG, "Error refreshing game. Error code : $err")
            } else {
                callback(game)
            }
        }

    }

    fun updateGame(activeGameId:String, currentGameState:GameState){
        GameService.updateGame(activeGameId, currentGameState) { game: Game?, err: Int? ->
            if(err != null){
                Log.d(TAG, "Error updating game. Error code : $err")
            }
        }
    }



}


