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


package com.example.android.asteroidsradar.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.example.android.asteroidsradar.databinding.FragmentMainBinding
import com.example.android.asteroidsradar.main.AsteroidLoadingStatus
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentDate = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentDate))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return formattedDateList
}

// convenience variables used to query the NASA API and our database locally
val today = getNextSevenDaysFormattedDates().first()
val sevenDaysFromToday = getNextSevenDaysFormattedDates().last()


fun FragmentMainBinding.showPlaceholdersWhileLoading(
    loadingStatus: AsteroidLoadingStatus,
    fragment: Fragment
) {
    when (loadingStatus) {
        AsteroidLoadingStatus.LOADING -> {
            noInternetText.visibility = View.GONE
            tryAgainButton.visibility = View.GONE
            nestedLayout.visibility = View.VISIBLE
            activityMainImageOfTheDay.visibility = View.GONE
            imagePlaceholderContainer.startShimmer()
            asteroidRecycler.visibility = View.GONE
            asteroidsPlaceholderContainer.startShimmer()
        }
        AsteroidLoadingStatus.DONE -> {
            imagePlaceholderContainer.stopShimmer()
            imagePlaceholderContainer.visibility = View.GONE
            activityMainImageOfTheDay.visibility = View.VISIBLE
            asteroidsPlaceholderContainer.stopShimmer()
            asteroidsPlaceholderContainer.visibility = View.GONE
            asteroidRecycler.visibility = View.VISIBLE
            //only show overflow filter menu when there's data available
            fragment.setHasOptionsMenu(true)

        }

        AsteroidLoadingStatus.ERROR -> {
            nestedLayout.visibility = View.GONE
            noInternetText.visibility = View.VISIBLE
            tryAgainButton.visibility = View.VISIBLE
            Timber.e("Couldn't fetch data")
        }
    }
}