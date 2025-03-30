package com.example.destination.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.destination.R
import com.example.destination.data.data.HomeItem
import com.example.destination.databinding.FragmentHomeBinding
import com.example.destination.ui.adapters.HomeAdapter
import com.example.destination.ui.additions.LanguagePreference
import com.example.destination.viewmodel.VocabViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var viewModel2: VocabViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val language = LanguagePreference.getLanguage(binding.root.context)
        Log.d("xxxx", language)

        // Instantiate VocabViewModel using the factory
        viewModel2 = ViewModelProvider(this)[VocabViewModel::class.java]

        binding.topicsRecyclerView.layoutManager = LinearLayoutManager(root.context)

        homeAdapter = HomeAdapter()
        binding.topicsRecyclerView.adapter = homeAdapter
        val homeItems = listOf(
            HomeItem("Fun and games", 3),
            HomeItem("Learning and doing", 6),
            HomeItem("Coming and going", 9),
            HomeItem("Friends and relations", 12),
            HomeItem("Buying and selling", 15),
            HomeItem("Inventions and discoveries", 18),
            HomeItem("Sending and receiving", 21),
            HomeItem("People and daily life", 24),
            HomeItem("Working and earning", 27),
            HomeItem("Body and lifestyle", 30),
            HomeItem("Creating and building", 33),
            HomeItem("Nature and the universe", 36),
            HomeItem("Laughing and crying", 39),
            HomeItem("Problems and solutions", 42)
        )


        homeAdapter.updateList(homeItems)

        // Set click listener
        homeAdapter.setOnClickItemListener(object : HomeAdapter.OnClickItemListener {
            override fun onClickItem(item: HomeItem) {
                val bundle = Bundle()
                bundle.putString("topicName", item.unitName)
                bundle.putInt("topicUnit", item.unitNumber)
                findNavController().navigate(R.id.action_navigation_home_to_vocabularyFragment, bundle)
            }
        })

        //save only once
        viewModel2.getRowCount { count->
            if (count==0){
                viewModel2.loadData("main_data.json")
            }
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}