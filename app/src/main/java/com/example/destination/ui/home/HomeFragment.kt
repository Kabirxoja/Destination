package com.example.destination.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.destination.R
import com.example.destination.databinding.FragmentHomeBinding
import com.example.destination.ui.home.vocabulary.ParentAdapter
import com.example.destination.ui.home.vocabulary.VocabularyViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]


        recyclerView = root.findViewById(R.id.parent_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        homeAdapter = HomeAdapter()
        recyclerView.adapter = homeAdapter

        // Set click listener
        homeAdapter.setOnClickItemListener(object : HomeAdapter.OnClickItemListener {
            override fun onClickItem(item: HomeItem) {
                Toast.makeText(binding.root.context, "Clicked: ${item.unitNumber}", Toast.LENGTH_SHORT).show()

                val bundle = Bundle()
                bundle.putString("topicName", item.unitName)
                bundle.putInt("topicUnit", item.unitNumber)
                findNavController().navigate(R.id.action_navigation_home_to_vocabularyFragment, bundle) // action ID from your nav_graph.xml

            }
        })

        viewModel.homeTopics.observe(viewLifecycleOwner) { homeItems ->
            homeAdapter.updateList(homeItems) // Use submitList() to update the adapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}