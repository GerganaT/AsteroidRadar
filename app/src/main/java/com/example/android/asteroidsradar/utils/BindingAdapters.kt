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
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asteroidsradar.R
import com.example.android.asteroidsradar.asteroidadapter.AsteroidAdapter
import com.example.android.asteroidsradar.domain.Asteroid
import com.example.android.asteroidsradar.domain.ImageOfTheDay
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription =
            context.getString(R.string.icon_hazardous_content_description)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription =
            context.getString(R.string.icon_non_hazardous_content_description)

    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription =
            context.getString(R.string.potentially_hazardous_asteroid_image)

    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = context.getString(R.string.non_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("imageOfTheDay")
fun showImageOfTheDay(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Picasso.with(imageView.context)
            .load(imgUrl)
            .into(imageView)

    }
}

@BindingAdapter("showPlaceholder")
fun showPlaceholder(imageView: ImageView, imageOfTheDay: ImageOfTheDay?) {
    if (imageOfTheDay == null) {
        imageView.setImageResource(R.drawable.no_image_available)
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
    } else {
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
    }
}

@BindingAdapter("hideViewIfNoImageToShow")
fun hideView(textView: TextView, imageOfTheDay: ImageOfTheDay?) {
    if (imageOfTheDay == null) {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
    }
}

@BindingAdapter("imageOfTheDayContentDescription")
fun setContentDescription(imageView: ImageView, imageTitle: String?) {
    val context = imageView.context
    val loadingImageDescription = context.getString(
        R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet
    )
    val todaysImageDescription = String.format(
        context.getString
            (R.string.nasa_picture_of_day_content_description_format), imageTitle
    )
    imageView.contentDescription = when (imageTitle) {
        null -> loadingImageDescription
        else -> todaysImageDescription
    }
}

@BindingAdapter("showAsteroids")
fun showAsteroids(recyclerView: RecyclerView, asteroidList: List<Asteroid>?) {
    asteroidList.let {
        val adapter = recyclerView.adapter as AsteroidAdapter
        adapter.submitList(asteroidList)
        recyclerView.scrollToPosition(0)
    }
}
