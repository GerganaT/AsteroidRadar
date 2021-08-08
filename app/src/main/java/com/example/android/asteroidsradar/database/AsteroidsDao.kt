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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.asteroidsradar.domain.Asteroid
import com.example.android.asteroidsradar.domain.ImageOfTheDay

@Dao
interface AsteroidsDao {

    @Query("SELECT * FROM asteroidsTable WHERE closeApproachDate = :today")
    suspend fun getTodaysAsteroids(today: String): List<Asteroid>

    /** The BETWEEN operator includes today's data as well */
    @Query(
        "SELECT * FROM asteroidsTable WHERE closeApproachDate BETWEEN " +
                ":today AND :sevenDaysFromToday ORDER BY closeApproachDate DESC"
    )
    suspend fun getNextSevenDaysAsteroids(
        today: String,
        sevenDaysFromToday: String
    ): List<Asteroid>

    @Query("SELECT * FROM asteroidsTable ORDER BY closeApproachDate DESC")
    suspend fun getAllSavedAsteroids(): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsteroids(vararg asteroids: Asteroid)

    @Query("DELETE FROM asteroidsTable WHERE closeApproachDate < :today")
    suspend fun deleteOlderAsteroids(today: String)

}

@Dao
interface ImageOfTheDayDao {


    /** Show either the today's image if available or else show the last saved image.
     * This is to ensure we show an image even if the user has had no internet recently */
    @Query("SELECT * FROM imagesTable ORDER BY date DESC LIMIT 1")
    suspend fun getMostRecentlyDownloadedImage(): ImageOfTheDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodaysImage(imageOfTheDay: ImageOfTheDay)
}