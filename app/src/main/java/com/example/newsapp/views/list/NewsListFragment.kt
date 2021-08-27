package com.example.newsapp.views.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsapp.databinding.NewsListFragmentBinding
import com.example.newsapp.views.SharedViewModel

class NewsListFragment : Fragment() {

    private var _binding: NewsListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewsListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.newsRv.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = NewsAdapter(NewsAdapter.NewsItemListener {
                viewModel.onNewsItemClicked(it)
                findNavController().navigate(
                    NewsListFragmentDirections
                        .actionNewsListFragmentToDetailsFragment()
                )
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}