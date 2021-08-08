/* Copyright 2021,  Gergana Kirilova

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.example.android.asteroidsradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.asteroidsradar.domain.Asteroid
import com.example.android.asteroidsradar.domain.ImageOfTheDay


@Database(entities = [Asteroid::class, ImageOfTheDay::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract val asteroidsDao: AsteroidsDao
    abstract val imageOfTheDayDao: ImageOfTheDayDao
}

@Volatile
private lateinit var INSTANCE: AsteroidsDatabase


fun getAsteroidsDatabase(context: Context): AsteroidsDatabase {

    if (!::INSTANCE.isInitialized) {
        synchronized(AsteroidsDatabase::class.java) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidsDatabase::class.java, "asteroids"
            ).build()
        }
    }

    return INSTANCE
}