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


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.asteroidsradar.R
import com.example.android.asteroidsradar.asteroidadapter.AsteroidAdapter
import com.example.android.asteroidsradar.asteroidadapter.AsteroidClickListener
import com.example.android.asteroidsradar.databinding.FragmentMainBinding
import com.example.android.asteroidsradar.utils.showPlaceholdersWhileLoading

class MainFragment : Fragment() {


    private val viewModel: MainViewModel by lazy {
        val fragmentActivity = requireNotNull(this.activity)

        ViewModelProvider(
            this, MainViewModel.MainViewModelFactory(
                fragmentActivity.application
            )
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)



        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        /** Show placeholders for the image of the day and the asteroids' list while fetching data */
        viewModel.asteroidLoadingStatus.observe(viewLifecycleOwner) { loadingStatus ->
            loadingStatus?.let {
                binding.showPlaceholdersWhileLoading(loadingStatus, this)

            }


        }

        val clickListener = AsteroidClickListener {
            val navDirections = MainFragmentDirections.actionShowDetail(it)
            findNavController().navigate(navDirections)
        }
        val adapter = AsteroidAdapter(clickListener)
        binding.asteroidRecycler.adapter = adapter

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        viewModel.applyFilterToResults(
            when (item.itemId) {
                R.id.show_today_menu ->
                    AsteroidFilter.TODAY
                R.id.show_week_menu ->
                    AsteroidFilter.WEEK
                R.id.show_all_menu ->
                    AsteroidFilter.ALL
                else -> return true
            }
        )

        return true
    }
}