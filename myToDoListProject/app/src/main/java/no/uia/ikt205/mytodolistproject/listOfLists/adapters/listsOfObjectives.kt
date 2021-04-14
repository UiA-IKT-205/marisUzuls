package no.uia.ikt205.mytodolistproject.listOfLists.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import no.uia.ikt205.mytodolistproject.databinding.VisualListRepresentationBinding
import no.uia.ikt205.mytodolistproject.listOfLists.dataClasses.listOfObjectiveLists
import no.uia.ikt205.mytodolistproject.listOfLists.managers.ListManager

class listOfListsAdapter (private var lists:List<listOfObjectiveLists>, private val onListClick:(listOfObjectiveLists) -> Unit) : RecyclerView.Adapter<listOfListsAdapter.ViewHolder>(){


    class ViewHolder(var binding:VisualListRepresentationBinding):RecyclerView.ViewHolder(binding.root){
        fun bindForLists(list: listOfObjectiveLists, onListClick:(listOfObjectiveLists) -> Unit){
            binding.listName.text = list.nameOfList
            binding.cardForLists.setOnClickListener {
                onListClick(list)
            }
        binding.listDeleteBtn.setOnClickListener {

            var listToDelete = list.nameOfList
            val db = FirebaseFirestore.getInstance()
            db.collection("ToDoLists").document(listToDelete).delete()
            ListManager.instanceListManager.listDataLoad(it.context)
            Toast.makeText(it.context, "List: "+listToDelete+" removed", Toast.LENGTH_LONG).show();
        }
        }
    }

    override fun getItemCount(): Int = lists.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = lists[position]
        holder.bindForLists(list,onListClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(VisualListRepresentationBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    fun updateToDoLists(newLists:List<listOfObjectiveLists>){
        lists = newLists
        notifyDataSetChanged()
    }
}