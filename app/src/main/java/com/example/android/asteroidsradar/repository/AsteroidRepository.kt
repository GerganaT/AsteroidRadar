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

package com.example.android.asteroidsradar.repository

import com.example.android.asteroidsradar.api.AsteroidApiService
import com.example.android.asteroidsradar.api.traverseAsteroidsJsonResult
import com.example.android.asteroidsradar.database.AsteroidsDatabase
import com.example.android.asteroidsradar.domain.Asteroid
import com.example.android.asteroidsradar.domain.ImageOfTheDay
import com.example.android.asteroidsradar.utils.Constants
import com.example.android.asteroidsradar.utils.sevenDaysFromToday
import com.example.android.asteroidsradar.utils.today
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber


//Class,containing methods , which abstract away  the interaction between
// the database and the Network API from the viewModel and the UI
class AsteroidRepository(private val asteroidsDatabase: AsteroidsDatabase) {

    /** Attempt to fetch data from the web-server and insert it in the database.*/
    suspend fun updateAsteroidsDatabase() {
        try {
            withContext(Dispatchers.IO) {

                val imageOfTheDay = AsteroidApiService.asteroidsApiService.getImageOfTheDay(
                    Constants.API_KEY
                )
                val asteroidsStringResponse =
                    AsteroidApiService.asteroidsApiService.getSevenDaysAsteroids(
                        today,
                        sevenDaysFromToday,
                        Constants.API_KEY
                    )
                val asteroidsArray =
                    traverseAsteroidsJsonResult(JSONObject(asteroidsStringResponse))
                        .toTypedArray()
                if (imageOfTheDay.mediaType != "video") {
                    asteroidsDatabase.imageOfTheDayDao.insertTodaysImage(imageOfTheDay)
                }
                asteroidsDatabase.asteroidsDao.insertAllAsteroids(*asteroidsArray)
            }
        } catch (exception: Exception) {
            Timber.e("Unable to update the database.")
            return
        }

    }

    suspend fun getTodaysAsteroidsFromDatabase(): List<Asteroid> {
        return asteroidsDatabase.asteroidsDao.getTodaysAsteroids(today)
    }

    suspend fun getNextSevenDaysAsteroidsFromDatabase(): List<Asteroid> {
        return asteroidsDatabase.asteroidsDao.getNextSevenDaysAsteroids(
            today,
            sevenDaysFromToday
        )

    }

    suspend fun getAllSavedAsteroidsFromDatabase(): List<Asteroid> {
        return asteroidsDatabase.asteroidsDao.getAllSavedAsteroids()
    }

    /** Delete asteroids older than today.*/
    suspend fun deleteOlderAsteroidsFromDatabase() {
        return asteroidsDatabase.asteroidsDao.deleteOlderAsteroids(today)
    }

    suspend fun getMostRecentlyDownloadedImageFromDatabase(): ImageOfTheDay {
        return asteroidsDatabase.imageOfTheDayDao.getMostRecentlyDownloadedImage()
    }
}