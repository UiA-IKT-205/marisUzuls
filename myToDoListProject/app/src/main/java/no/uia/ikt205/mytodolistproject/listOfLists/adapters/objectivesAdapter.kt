package no.uia.ikt205.mytodolistproject.listOfLists.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.visual_objective_list_represantation.view.*
import no.uia.ikt205.mytodolistproject.databinding.VisualObjectiveListRepresantationBinding
import no.uia.ikt205.mytodolistproject.listHolder
import no.uia.ikt205.mytodolistproject.listOfLists.anOpenObjectiveActivity
import no.uia.ikt205.mytodolistproject.listOfLists.dataClasses.listOfObjectiveLists
import no.uia.ikt205.mytodolistproject.listOfLists.dataClasses.openObjectivesList
import no.uia.ikt205.mytodolistproject.listOfLists.managers.listObjectiveManager


class objectivesAdapter (private var lists:List<openObjectivesList>): RecyclerView.Adapter<objectivesAdapter.ViewHolder>() {

    class ViewHolder(var binding: VisualObjectiveListRepresantationBinding):RecyclerView.ViewHolder(binding.root){
        fun bindForListObjectives(list: openObjectivesList){
            binding.objectiveName.text = list.objective
            binding.objectiveDeleteBtn.setOnClickListener {

                var objectiveFromList =listHolder.listChosen?.nameOfList.toString()
                var objectiveToDelete = list.objective
                val db = FirebaseFirestore.getInstance()
                db.collection("ToDoLists").document(objectiveFromList).collection(objectiveFromList).document(objectiveToDelete).delete()
                Toast.makeText(it.context, "List: "+objectiveToDelete+" removed", Toast.LENGTH_LONG).show()

                //// Absolutt ikke pent men hadde veldig lite tid for å fikse dette
                listObjectiveManager.instanceObjectiveManager.listObjectiveDataLoad(it.context)

            }

    }
    }

    private val db = FirebaseFirestore.getInstance()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): objectivesAdapter.ViewHolder {
        return objectivesAdapter.ViewHolder(VisualObjectiveListRepresantationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun updateObjectives(newObjectives:List<openObjectivesList>){
        lists = newObjectives
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = lists.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val objectivesOfAList = lists[position]
        holder.bindForListObjectives(objectivesOfAList)
        }



}



