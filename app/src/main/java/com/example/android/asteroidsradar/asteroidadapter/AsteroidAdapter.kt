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

package com.example.android.asteroidsradar.asteroidadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asteroidsradar.databinding.ListItemBinding
import com.example.android.asteroidsradar.domain.Asteroid

class AsteroidAdapter(val asteroidClickListener: AsteroidClickListener) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(
        AsteroidDiffCallBack
    ) {

    companion object AsteroidDiffCallBack : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        return holder.onBind(getItem(position), asteroidClickListener)
    }

    class AsteroidViewHolder private constructor(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(asteroid: Asteroid, asteroidClickListener: AsteroidClickListener) {
            binding.asteroid = asteroid
            binding.clickListener = asteroidClickListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(inflater, parent, false)
                return AsteroidViewHolder(binding)
            }
        }

    }


}

class AsteroidClickListener(val clickAction: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickAction(asteroid)
}
