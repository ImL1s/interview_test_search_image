package com.iml1s.interview.test.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.iml1s.interview.test.databinding.FragmentSearchBinding
import com.iml1s.interview.test.datasource.defaultApiService
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater).apply {
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {
                Log.d("GG", "onSearchStateChanged: $enabled")
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                Log.d("GG", "onSearchConfirmed: $text")
            }

            override fun onButtonClicked(buttonCode: Int) {
                Log.d("GG", "onButtonClicked: $buttonCode")
            }
        })

    }.root


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        defaultApiService.search(keyword = "shiba inu")
            .onEach {
                Timber.d("search result: $it")
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

}
