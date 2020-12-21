package com.iml1s.interview.test.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iml1s.interview.test.databinding.FragmentSearchBinding
import com.mancj.materialsearchbar.MaterialSearchBar

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater).apply {
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {
                Log.d("GG","onSearchStateChanged: $enabled")
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                Log.d("GG","onSearchConfirmed: $text")
            }

            override fun onButtonClicked(buttonCode: Int) {
                Log.d("GG","onButtonClicked: $buttonCode")
            }
        })

    }.root

}
