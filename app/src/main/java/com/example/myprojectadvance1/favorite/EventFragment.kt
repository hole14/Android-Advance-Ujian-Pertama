package com.example.myprojectadvance1.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectadvance1.data.source.Result
import com.example.myprojectadvance1.databinding.FragmentEventBinding

class EventFragment : Fragment() {
    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
        const val TAB_FAVORITE = "favorite"
    }
    private var tabName: String? = null

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: EventViewModel by viewModels {
            factory
        }

        val newsAdapter = EventAdapter { news ->
            if (news.isFavorited){
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }

        if (tabName == TAB_NEWS){
            viewModel.getHeadlineNews().observe(viewLifecycleOwner){ result ->
                if (result != null){
                    when(result){
                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan" + result.error, Toast.LENGTH_SHORT).show()
                        }
                        Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            val newsData = result.data
                            newsAdapter.submitList(newsData)
                        }
                    }
                }
            }
        } else if (tabName == TAB_FAVORITE) {
            viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { bookmarkedNews ->
                binding?.progressBar?.visibility = View.GONE
                newsAdapter.submitList(bookmarkedNews)
            }
        }

        binding?.rvEvent?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }
}