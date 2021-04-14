package no.uia.ikt205.mytodolistproject

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import no.uia.ikt205.mytodolistproject.databinding.ActivityMainBinding
import no.uia.ikt205.mytodolistproject.listOfLists.managers.ListManager
import no.uia.ikt205.mytodolistproject.listOfLists.anOpenObjectiveActivity
import no.uia.ikt205.mytodolistproject.listOfLists.dataClasses.listOfObjectiveLists
import no.uia.ikt205.mytodolistproject.listOfLists.adapters.listOfListsAdapter


val projectTag:String = "myToDoListProject"

class listHolder{

    companion object{
        var listChosen:listOfObjectiveLists? = null
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var anonymousAuth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        binding.overviewOfLists.layoutManager = LinearLayoutManager(this)
        binding.overviewOfLists.adapter = listOfListsAdapter(emptyList<listOfObjectiveLists>(), this::onListClick)

        anonymousAuth = Firebase.auth
        logInSuccessCheck()


        ListManager.instanceListManager.currentList = {
            (binding.overviewOfLists.adapter as listOfListsAdapter).updateToDoLists(it)
        }

        ListManager.instanceListManager.listDataLoad(this)


        binding.addNewListBtn.setOnClickListener{

            val creationNotiffier = AlertDialog.Builder(this)
            val listNameInput = EditText(this)

            creationNotiffier.setMessage("Add new to do list")
            creationNotiffier.setTitle("Add a new objective to a list")
            creationNotiffier.setView(listNameInput)
            creationNotiffier.setPositiveButton("Add"){dialog, i ->

                var listValueName = listNameInput.text.toString()

                if(listValueName.isNotEmpty()) {
                    val list = hashMapOf(
                        "List name" to listValueName
                    )

                    db.collection("ToDoLists").document(listValueName).set(list)

                    Toast.makeText(this, "list saved", Toast.LENGTH_LONG).show()
                    ListManager.instanceListManager.listDataLoad(this)
                }
            }
            creationNotiffier.show()

        }


    }

    private fun logInSuccessCheck(){
        anonymousAuth.signInAnonymously().addOnSuccessListener { Log.d(projectTag, "success") }
    }




    private fun onListClick(listOfLists: listOfObjectiveLists):Unit{
        val intent = Intent(this,anOpenObjectiveActivity::class.java)
        listHolder.listChosen = listOfLists
        startActivity(intent)
    }


}

