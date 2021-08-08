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

package com.example.android.asteroidsradar.api

import com.example.android.asteroidsradar.domain.ImageOfTheDay
import com.example.android.asteroidsradar.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.reflect.Type

/** Idea for multiple converters taken from this article:
https://medium.com/mindorks/retrofit-with-multiple-converters-71ecd4042681 */


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class MoshiConverter

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ScalarsConverter


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiOrScalarsConverterFactory())
    .baseUrl(BASE_URL)
    .build()


interface AsteroidsAPIService {

    @ScalarsConverter
    @GET("neo/rest/v1/feed")
    suspend fun getSevenDaysAsteroids(
        @Query("start_date") today: String,
        @Query("end_date") weekAhead: String,
        @Query("api_key") apiKey: String
    ): String


    @MoshiConverter
    @GET("planetary/apod")
    suspend fun getImageOfTheDay(
        @Query("api_key") apiKey: String
    ): ImageOfTheDay
}


object AsteroidApiService {
    val asteroidsApiService: AsteroidsAPIService by lazy {
        retrofit.create(AsteroidsAPIService::class.java)
    }
}

class MoshiOrScalarsConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        annotations.forEach { annotation ->
            when (annotation) {
                is ScalarsConverter -> {
                    return ScalarsConverterFactory.create()
                        .responseBodyConverter(type, annotations, retrofit)
                }
                is MoshiConverter -> {
                    return MoshiConverterFactory.create(moshi)
                        .responseBodyConverter(type, annotations, retrofit)
                }
            }
        }
        return super.responseBodyConverter(type, annotations, retrofit)
    }
}