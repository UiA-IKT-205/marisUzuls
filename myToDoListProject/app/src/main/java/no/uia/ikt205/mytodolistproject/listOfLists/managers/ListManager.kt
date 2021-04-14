package no.uia.ikt205.mytodolistproject.listOfLists.managers

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import no.uia.ikt205.mytodolistproject.MainActivity
import no.uia.ikt205.mytodolistproject.listOfLists.dataClasses.listOfObjectiveLists


class ListManager {

    private lateinit var overviewOfLists: MutableList<listOfObjectiveLists>
    var currentList:((List<listOfObjectiveLists>)->Unit)? = null
    var listUpdate:((nList: listOfObjectiveLists)->Unit)?= null
    private val db = FirebaseFirestore.getInstance()


    fun listDataLoad(context: Context){
        overviewOfLists = mutableListOf()

        db.collection("ToDoLists")
            .get()
            .addOnSuccessListener { lists ->
                for (list in lists) {
                    addNewList(listOfObjectiveLists(list.data.get("List name") as String))
                }
            }
        
        currentList?.invoke(overviewOfLists)
    }

    fun addNewList(nList: listOfObjectiveLists){

        overviewOfLists.add(nList)
        currentList?.invoke(overviewOfLists)

    }

    fun removeList(nList: listOfObjectiveLists){

        overviewOfLists.remove(nList)
        currentList?.invoke(overviewOfLists)

    }

    fun updateList(nList: listOfObjectiveLists){

        listUpdate?.invoke(nList)

    }


    companion object{

        val instanceListManager = ListManager()

    }

}