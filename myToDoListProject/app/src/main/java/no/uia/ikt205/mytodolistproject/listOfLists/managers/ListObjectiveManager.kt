package no.uia.ikt205.mytodolistproject.listOfLists.managers

import android.content.Context
import no.uia.ikt205.mytodolistproject.listOfLists.dataClasses.openObjectivesList

class listObjectiveManager {

    private lateinit var objectivesOfList: MutableList<openObjectivesList>
    var currentObjectiveList:((List<openObjectivesList>)->Unit)? = null
    var objectiveListUpdate:((nList: openObjectivesList)->Unit)?= null


    fun listObjectiveDataLoad(context: Context){

        objectivesOfList = mutableListOf()
        currentObjectiveList?.invoke(objectivesOfList)

    }

    fun addNewObjective(nList: openObjectivesList){

        objectivesOfList.add(nList)
        currentObjectiveList?.invoke(objectivesOfList)

    }

    fun removeList(nList: openObjectivesList){

        objectivesOfList.remove(nList)
        currentObjectiveList?.invoke(objectivesOfList)

    }

    fun updateList(nList: openObjectivesList){

        objectiveListUpdate?.invoke(nList)

    }


    companion object{

        val instanceObjectiveManager = listObjectiveManager()

    }


}