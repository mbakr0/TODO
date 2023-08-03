package online.mohmedbakr.todo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): LiveData<List<Task>>

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("DELETE FROM tasks")
    fun deleteAll()
}