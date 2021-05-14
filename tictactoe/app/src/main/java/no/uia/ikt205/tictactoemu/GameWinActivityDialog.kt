package no.uia.ikt205.tictactoemu

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import no.uia.ikt205.tictactoemu.databinding.DialogGameWinBinding


class GameWinDialog(var winningSymbol: Char) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder: androidx.appcompat.app.AlertDialog.Builder =
                androidx.appcompat.app.AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = DialogGameWinBinding.inflate(inflater)


            builder.apply {
                if (winningSymbol != 'D'){
                    setTitle("Winner!")
                }
                else{
                    setTitle("Draw!")
                }
                binding.winnerSymbol.text = winningSymbol.toString()
                setPositiveButton("Leave game") { _, _ ->
                    activity!!.finish()
                }

                setView(binding.root)
            }

            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
