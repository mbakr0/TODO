package online.mohmedbakr.todo.ui.mainFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import online.mohmedbakr.todo.alarm.Alarm
import online.mohmedbakr.todo.database.Task
import online.mohmedbakr.todo.database.TaskDao
import online.mohmedbakr.todo.databinding.ListItemBinding
import java.util.Collections




class RecyclerViewAdapter(private val viewModel: MainFragmentViewModel) : ListAdapter<Task, RecyclerViewAdapter.TaskViewHolder>(TaskDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun onItemDismiss(position: Int, context: Context) {
        viewModel.removeTask(currentList[position])
        Alarm.removeAlarm(context, currentList[position])
        Toast.makeText(context,"Removed",Toast.LENGTH_SHORT).show()
    }

    class TaskViewHolder private constructor (private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Task) {
            binding.task = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TaskViewHolder {
                val layoutInflater =
                    LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return TaskViewHolder(binding)
            }
        }

    }

    class TaskDiffUtil: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.uid == newItem.uid
        }

    }

    class ListItemTouchHelper(private val adapter: RecyclerViewAdapter) : ItemTouchHelper.Callback(){
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
        ): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.onItemDismiss(viewHolder.adapterPosition,viewHolder.itemView.context)
        }

    }
}