package com.franjo.github.presentation.features.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.franjo.github.presentation.OnItemClickListener
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentSearchRepositoryBinding
import com.franjo.github.presentation.util.generateDividerDecoration
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SearchRepositoryFragment : Fragment() {

    //override fun getFragmentView(): Int = R.layout.fragment_search_repository


    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    private val viewModel: SearchRepositoryViewModel by lazy {
        ViewModelProvider(this, modelFactory).get(SearchRepositoryViewModel::class.java)
    }

    private var searchResultAdapter: SearchRepositoryAdapter? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchRepositoryBinding.inflate(inflater)

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setAdapter(binding)
        observeDetailsNavigation()

        return binding.root
    }

    private fun setAdapter(binding: FragmentSearchRepositoryBinding) {
        searchResultAdapter = SearchRepositoryAdapter(listener = OnItemClickListener { repository ->
            viewModel.toRepositoryDetailsNavigate(repository)
        })
        binding.root.findViewById<RecyclerView>(R.id.rv_search).apply {
            adapter = searchResultAdapter
            addItemDecoration(context.generateDividerDecoration())
        }
    }


    private fun observeDetailsNavigation() {
        viewModel.navigateToRepositoryDetails.observe(viewLifecycleOwner, Observer { repository ->
            val action =
                SearchRepositoryFragmentDirections.actionSearchRepositoryFragmentToRepositoryDetailsFragment(
                    repository
                )
            NavHostFragment.findNavController(this).navigate(action)
            viewModel.onRepositoryDetailsNavigated()
        })
    }

}