package no.uia.ikt205.tictactoemu.gameStates

import android.os.Bundle
import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import no.uia.ikt205.tictactoemu.databinding.DialogCreateGameBinding
import java.lang.ClassCastException
import androidx.fragment.app.DialogFragment


class GameInstanceCreate() : DialogFragment() {

    private lateinit var listener: DialogsForGameInterface

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = DialogCreateGameBinding.inflate(inflater)

            builder.apply {
                setTitle("Create a game")
                setPositiveButton("Create") { _, _ ->
                    if(binding.username.text.toString() != ""){
                        listener.onDialogCreateGame(binding.username.text.toString())
                    }
                }
                setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
                setView(binding.root)
            }

            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogsForGameInterface
        } catch (e:ClassCastException){
            throw ClassCastException(("$context must implement GameDialogListener"))

        }
    }

}
