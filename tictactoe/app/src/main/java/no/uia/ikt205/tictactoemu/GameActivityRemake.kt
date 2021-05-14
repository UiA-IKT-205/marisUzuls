package no.uia.ikt205.tictactoemu


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import no.uia.ikt205.tictactoemu.ApplicationInterface.Companion.context
import no.uia.ikt205.tictactoemu.api.data.Game
import no.uia.ikt205.tictactoemu.api.data.GameState
import no.uia.ikt205.tictactoemu.databinding.ActivityGameRemakeBinding


class GameActivityRemake : AppCompatActivity() {

    private lateinit var binding: ActivityGameRemakeBinding
    private var currentGameId: String = ""
    private var player1Name: String = "Open slot"
    private var player2Name: String = "Open slot"
    private var player1AssignedMarker: Char = 'X'
    private var player2AssignedMarker: Char = 'O'
    private var turnCounter: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameRemakeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var game: Game? = intent.getParcelableExtra("game")
        playerConnectionCheck(game)

        binding.buttonUpdate.setOnClickListener {
            GameManager.pollGame(currentGameId){
                if (it != null) {
                    Log.d("tag", it.players.toString())
                    if (it.players[1]!= null){
                        player2Name = it.players[1]
                        binding.playerNameTwo.text  = player2Name
                    }
                }
            }
            if (game != null) {
                GameManager.updateGame(currentGameId,game.state)
                GameManager.pollGame(currentGameId){ it ->
                    if (it != null) {
                        Log.d("tag", it.state.toString())
                        loadGameTableState(game.state)
                    }
                }
            }
        }


        binding.playerNameOne.text  = player1Name
        binding.gameKeyVisual.text = currentGameId
        binding.playerNameTwo.text  = player2Name


        createOnclickListeners(game)



    }

    private fun loadGameTableState(currentGameState: GameState) {

        if (currentGameState != null) {
            if (currentGameState[0][0] != '0'){
                binding.r11.text = currentGameState[0][0].toString()
            }
            if (currentGameState[0][1] != '0'){
                binding.r12.text = currentGameState[0][1].toString()
            }
            if (currentGameState[0][2] != '0'){
                binding.r13.text = currentGameState[0][2].toString()
            }
            if (currentGameState[1][0] != '0'){
                binding.r21.text = currentGameState[1][0].toString()
            }
            if (currentGameState[1][1] != '0'){
                binding.r22.text = currentGameState[1][1].toString()
            }
            if (currentGameState[1][2] != '0'){
                binding.r23.text = currentGameState[1][2].toString()
            }
            if (currentGameState[2][0] != '0'){
                binding.r31.text = currentGameState[2][0].toString()
            }
            if (currentGameState[2][1] != '0'){
                binding.r32.text = currentGameState[2][1].toString()
            }
            if (currentGameState[2][2] != '0'){
                binding.r33.text = currentGameState[2][2].toString()
            }

        }

    }

    private fun markATile(game: Game?, indexListLayerOne:Int, indexListLayerTwo: Int) {
        if (turnCounter) {
            if (game != null && game.state[indexListLayerOne][indexListLayerTwo] == '0') {
                game.state[indexListLayerOne][indexListLayerTwo] = player1AssignedMarker
                game.state.let {
                    GameManager.updateGame(game.gameId, it)
                }
                turnCounter = false
                winCondition(game.state)
            }
        }
        if (!turnCounter ) {
            if (game != null && game.state[indexListLayerOne][indexListLayerTwo] == '0') {
                game.state[indexListLayerOne][indexListLayerTwo] = player2AssignedMarker
                game.state.let {
                    GameManager.updateGame(game.gameId, it)
                }
                turnCounter = true
                winCondition(game.state)
            }
        }
        game?.let { loadGameTableState(it.state) }
    }

    private fun createOnclickListeners(game: Game?){
        if (game !=null) {
            binding.r11.setOnClickListener{
                markATile(game,0,0)
            }
            binding.r12.setOnClickListener{
                markATile(game,0,1)
            }
            binding.r13.setOnClickListener{
                markATile(game,0,2)
            }
            binding.r21.setOnClickListener{
                markATile(game,1,0)
            }
            binding.r22.setOnClickListener{
                markATile(game,1,1)
            }
            binding.r23.setOnClickListener{
                markATile(game,1,2)
            }
            binding.r31.setOnClickListener{
                markATile(game,2,0)
            }
            binding.r32.setOnClickListener{
                markATile(game,2,1)
            }
            binding.r33.setOnClickListener{
                markATile(game,2,2)
            }
        }

    }

    private fun playerConnectionCheck(game: Game?){
        if (game != null) {
            currentGameId = game.gameId
            if (game.players.size != 0){
                player1Name = game.players[0]
                if (game.players.size == 2){
                    player2Name = game.players[1]
                }
            }
            if(game.players.size > 2){
                player1Name = game.players[0]
                player2Name = game.players[1]
                Toast.makeText(context, "Sorry, too many players!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun winCondition(currentGameState: GameState) {

        var lastSymbolIndex = false
        // x wins by row
        if (currentGameState[0][0] == 'X' && currentGameState[0][1] == 'X' && currentGameState[0][2] == 'X'){
            winGameDialog('X')
            lastSymbolIndex = true
        }
        if (currentGameState[1][0] == 'X' && currentGameState[1][1] == 'X' && currentGameState[1][2] == 'X'){
            winGameDialog('X')
            lastSymbolIndex = true
        }
        if (currentGameState[2][0] == 'X' && currentGameState[2][1] == 'X' && currentGameState[2][2] == 'X'){
            winGameDialog('X')
            lastSymbolIndex = true
        }

        // // x wins by column
        if (currentGameState[0][0] == 'X' && currentGameState[1][0] == 'X' && currentGameState[2][0] == 'X'){
            winGameDialog('X')
            lastSymbolIndex = true
        }
        if (currentGameState[0][1] == 'X' && currentGameState[1][1] == 'X' && currentGameState[2][1] == 'X'){
            winGameDialog('X')
            lastSymbolIndex = true
        }
        if (currentGameState[0][2] == 'X' && currentGameState[1][2] == 'X' && currentGameState[2][2] == 'X'){
            winGameDialog('X')
            lastSymbolIndex = true
        }

        // x wins by diagonal
        if (currentGameState[0][0] == 'X' && currentGameState[1][1] == 'X' && currentGameState[2][2] == 'X'){
            winGameDialog('X')
            lastSymbolIndex = true
        }
        if (currentGameState[2][0] == 'X' && currentGameState[1][1] == 'X' && currentGameState[0][2] == 'X'){
            winGameDialog('X')
            lastSymbolIndex = true
        }

        // O wins by row
        if (currentGameState[0][0] == 'O' && currentGameState[0][1] == 'O' && currentGameState[0][2] == 'O'){
            winGameDialog('O')
            lastSymbolIndex = true
        }
        if (currentGameState[1][0] == 'O' && currentGameState[1][1] == 'O' && currentGameState[1][2] == 'O'){
            winGameDialog('O')
            lastSymbolIndex = true
        }
        if (currentGameState[2][0] == 'O' && currentGameState[2][1] == 'O' && currentGameState[2][2] == 'O'){
            winGameDialog('O')
            lastSymbolIndex = true
        }

        // y wins by column
        if (currentGameState[0][0] == 'O' && currentGameState[1][0] == 'O' && currentGameState[2][0] == 'O'){
            winGameDialog('O')
            lastSymbolIndex = true
        }
        if (currentGameState[0][1] == 'O' && currentGameState[1][1] == 'O' && currentGameState[2][1] == 'O'){
            winGameDialog('O')
            lastSymbolIndex = true
        }
        if (currentGameState[0][2] == 'O' && currentGameState[1][2] == 'O' && currentGameState[2][2] == 'O'){
            winGameDialog('O')
            lastSymbolIndex = true
        }

        // y wins by diagonal
        if (currentGameState[0][0] == 'O' && currentGameState[1][1] == 'O' && currentGameState[2][2] == 'O'){
            winGameDialog('O')
            lastSymbolIndex = true
        }
        if (currentGameState[2][0] == 'O' && currentGameState[1][1] == 'O' && currentGameState[0][2] == 'O'){
            winGameDialog('O')
            lastSymbolIndex = true
        }


        // check for draw
        if(!currentGameState.flatten().contains('0') && !lastSymbolIndex){
            Log.d("tag", currentGameState.toString())
            winGameDialog('D')
        }
    }
    private fun winGameDialog(winningSymbol:Char){
        val newGameCreatePrompt = GameWinDialog(winningSymbol)
        newGameCreatePrompt.show(supportFragmentManager,"GameWinDialogFragment")
    }



}