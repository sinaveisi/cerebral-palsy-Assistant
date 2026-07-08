package com.example.cp.di


import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cp.data.local.dao.EducateDAO
import com.example.cp.data.local.database.AppDatabase
import com.example.cp.data.local.prepopulation.PrepopulateData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        // Use Provider for lazy injection to prevent a circular dependency crash
        daoProvider: Provider<EducateDAO>
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // This block runs only ONCE when the database is first created
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = daoProvider.get()
                        PrepopulateData.getInitialTopics().forEach { topic ->
                            // 1. Insert the parent title and get its new ID
                            val parentId = dao.insertTitle(topic.title)

                            // 2. Use that ID to insert all its children
                            topic.descriptions.forEach { description ->
                                dao.insertDescription(
                                    description.copy(parentOwnerId = parentId.toInt())
                                )
                            }
                        }
                    }
                }
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDao(database: AppDatabase): EducateDAO { // Ensure this returns EducateDAO
        return database.educateDAO()
    }
}