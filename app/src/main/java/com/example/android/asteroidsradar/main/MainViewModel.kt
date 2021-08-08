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

package com.example.android.asteroidsradar.main

import android.app.Application
import androidx.lifecycle.*
import com.example.android.asteroidsradar.database.getAsteroidsDatabase
import com.example.android.asteroidsradar.domain.Asteroid
import com.example.android.asteroidsradar.domain.ImageOfTheDay
import com.example.android.asteroidsradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber

enum class AsteroidFilter {
    TODAY, WEEK, ALL
}

enum class AsteroidLoadingStatus {
    LOADING, DONE, ERROR
}

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val _asteroidResult = MutableLiveData<List<Asteroid>>()
    val asteroidResult: LiveData<List<Asteroid>>
        get() = _asteroidResult

    private val _asteroidLoadingStatus = MutableLiveData<AsteroidLoadingStatus>()
    val asteroidLoadingStatus: LiveData<AsteroidLoadingStatus>
        get() = _asteroidLoadingStatus

    private val _imageOfTheDay = MutableLiveData<ImageOfTheDay>()
    val imageOfTheDay: LiveData<ImageOfTheDay>
        get() = _imageOfTheDay

    private val asteroidsDatabase = getAsteroidsDatabase(application)
    private val asteroidsRepository = AsteroidRepository(asteroidsDatabase)


    init {
        getAsteroidsAndImageData()
    }

    fun getAsteroidsAndImageData() {
        try {
            viewModelScope.launch {
                _asteroidLoadingStatus.value = AsteroidLoadingStatus.LOADING
                asteroidsRepository.updateAsteroidsDatabase()
                _imageOfTheDay.value =
                    asteroidsRepository.getMostRecentlyDownloadedImageFromDatabase()
                _asteroidResult.value = asteroidsRepository.getAllSavedAsteroidsFromDatabase()
                if (asteroidResult.value?.isEmpty() == true) {
                    _asteroidLoadingStatus.value = AsteroidLoadingStatus.ERROR
                } else {
                    _asteroidLoadingStatus.value = AsteroidLoadingStatus.DONE

                }

            }
        } catch (exception: Exception) {
            _asteroidLoadingStatus.value = AsteroidLoadingStatus.ERROR
            Timber.e("Couldn't fetch data")
        }

    }


    fun applyFilterToResults(asteroidFilter: AsteroidFilter) {
        viewModelScope.launch {
            _asteroidResult.value = when (asteroidFilter) {
                AsteroidFilter.TODAY ->
                    asteroidsRepository.getTodaysAsteroidsFromDatabase()

                AsteroidFilter.WEEK ->
                    asteroidsRepository.getNextSevenDaysAsteroidsFromDatabase()

                else -> asteroidsRepository.getAllSavedAsteroidsFromDatabase()

            }
        }

    }


    class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(application) as T
            }

            throw  IllegalArgumentException("Unable to construct ViewModel")
        }

    }


}