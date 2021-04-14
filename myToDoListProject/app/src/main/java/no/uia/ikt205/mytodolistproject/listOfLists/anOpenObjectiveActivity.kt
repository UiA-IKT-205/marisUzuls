package no.uia.ikt205.mytodolistproject.listOfLists

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import no.uia.ikt205.mytodolistproject.databinding.ActivityAnOpenObjectiveBinding
import no.uia.ikt205.mytodolistproject.listHolder
import no.uia.ikt205.mytodolistproject.listOfLists.adapters.objectivesAdapter
import no.uia.ikt205.mytodolistproject.listOfLists.dataClasses.listOfObjectiveLists
import no.uia.ikt205.mytodolistproject.listOfLists.dataClasses.openObjectivesList
import no.uia.ikt205.mytodolistproject.listOfLists.managers.ListManager
import no.uia.ikt205.mytodolistproject.listOfLists.managers.listObjectiveManager

class anOpenObjectiveActivity : Activity() {

    private lateinit var binding:ActivityAnOpenObjectiveBinding
    private lateinit var list: listOfObjectiveLists
    private val db = FirebaseFirestore.getInstance()
    private var ListName:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnOpenObjectiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listValue = listHolder.listChosen

        if (listValue != null) {
            list = listValue
            Log.i("Details",listValue.toString())
            var tempObjective: openObjectivesList
            ListName = list.nameOfList

            db.collection("ToDoLists").document(ListName).collection(ListName).get()
            .addOnSuccessListener { objectiveList ->
                for (element in objectiveList) {
                    val checkStatus = element.data.toString().contains("true")
                    if (checkStatus) {
                        tempObjective = openObjectivesList(element.id, status = true)
                    } else {
                        tempObjective = openObjectivesList(element.id, status = false)
                    }
                    listObjectiveManager.instanceObjectiveManager.addNewObjective(tempObjective)
                }
            }


        binding.listTitle.text = ListName

        binding.objectiveListed.layoutManager = LinearLayoutManager(this)
        binding.objectiveListed.adapter = objectivesAdapter(emptyList<openObjectivesList>())


        listObjectiveManager.instanceObjectiveManager.currentObjectiveList = {
            (binding.objectiveListed.adapter as objectivesAdapter).updateObjectives(it)
        }

        listObjectiveManager.instanceObjectiveManager.listObjectiveDataLoad(this)



        binding.addNewObjectiveBtn.setOnClickListener{

            val creationNotiffier = AlertDialog.Builder(this)
            val objectiveNameInput = EditText(this)

            creationNotiffier.setMessage("A new objective")
            creationNotiffier.setTitle("Add a new objective to a list")
            creationNotiffier.setView(objectiveNameInput)
            creationNotiffier.setPositiveButton("Add"){dialog, i ->

                val objectiveName = objectiveNameInput.text.toString()

                if(objectiveName.isNotEmpty()) {

                    var newObjective = openObjectivesList(objectiveName, false)
                    val checkBoxStatus = hashMapOf(
                        "status" to false
                    )

                    db.collection("ToDoLists").document(ListName).collection(ListName).document(objectiveName).set(checkBoxStatus)

                    Toast.makeText(this, "objective saved", Toast.LENGTH_LONG).show()
                    listObjectiveManager.instanceObjectiveManager.addNewObjective(newObjective)
                }
            }

            ListManager.instanceListManager.listDataLoad(this)
            creationNotiffier.show()

            }

        }
        else{
            finish()
        }
    }
}


