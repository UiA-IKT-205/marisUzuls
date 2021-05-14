package no.uia.ikt205.tictactoemu.gameStates

interface DialogsForGameInterface {
    fun onDialogCreateGame(playerSpecifiedName:String)
    fun onDialogJoinGame(playerSpecifiedName: String, activeGameId:String)
}

