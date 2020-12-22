package com.iml1s.interview.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.iml1s.interview.test.databinding.FragmentSearchBinding
import com.iml1s.interview.test.viewmodel.SearchViewModel
import com.mancj.materialsearchbar.MaterialSearchBar
import timber.log.Timber


class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater).apply {
        viewModel = this@SearchFragment.viewModel
        recyclerView.adapter = SearchResultAdapter()
        lifecycleOwner = this@SearchFragment.viewLifecycleOwner
        searchBar.searchEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {
                Timber.d("onSearchStateChanged: $enabled")
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                Timber.d("onSearchConfirmed: $text")
                val imm = getSystemService(requireContext(), InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(searchBar.windowToken, 0)
                searchBar.closeSearch()
            }

            override fun onButtonClicked(buttonCode: Int) {
                Timber.d("onButtonClicked: $buttonCode")
            }
        })
    }.root

}
