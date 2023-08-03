package online.mohmedbakr.todo.ui.mainFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import online.mohmedbakr.todo.database.Task
import online.mohmedbakr.todo.database.TaskDao

class MainFragmentViewModel(private val taskDao: TaskDao) :ViewModel() {

    val list = taskDao.getAll()
    fun removeTask(task: Task){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                taskDao.delete(task)
            }
        }
    }
}