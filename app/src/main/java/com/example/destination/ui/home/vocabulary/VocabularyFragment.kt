package com.example.destination.ui.home.vocabulary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.destination.R
import com.example.destination.databinding.FragmentHomeBinding

class VocabularyFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!


    private lateinit var recyclerView: RecyclerView
    private lateinit var parentAdapter: ParentAdapter
    private lateinit var viewModel: VocabularyViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this)[VocabularyViewModel::class.java]


        val args = arguments // arguments is a nullable Bundle
        val topicUnit = args?.getString("topicUnit")
        val topicNumber = args?.getInt("topicNumber")
        Toast.makeText(binding.root.context, "$topicUnit   - $topicNumber", Toast.LENGTH_SHORT).show()


        recyclerView = root.findViewById(R.id.parent_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(root.context)

        parentAdapter = ParentAdapter()
        recyclerView.adapter = parentAdapter

        viewModel.parentItems.observe(viewLifecycleOwner) { parentItems ->
            parentAdapter.submitList(parentItems)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}