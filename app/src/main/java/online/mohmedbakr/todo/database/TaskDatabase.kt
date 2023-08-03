package online.mohmedbakr.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

}

object LocalDB {

    fun createTaskDao(context: Context): TaskDao {
        return Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java, "tasks.db"
        )
            .fallbackToDestructiveMigration()
            .build().taskDao()
    }
}