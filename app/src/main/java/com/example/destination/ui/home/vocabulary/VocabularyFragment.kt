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
import com.example.destination.databinding.FragmentVocabularyBinding
import com.example.destination.ui.notes.Word
import kotlin.math.abs

class VocabularyFragment : Fragment() {
    private var _binding: FragmentVocabularyBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentAdapter: ParentAdapter
    private lateinit var viewModel: VocabularyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVocabularyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this)[VocabularyViewModel::class.java]


        val args = arguments // arguments is a nullable Bundle
        val topicUnit = args?.getString("topicUnit")
        val topicNumber = args?.getInt("topicNumber")
        Toast.makeText(binding.root.context, "$topicUnit   - $topicNumber", Toast.LENGTH_SHORT)
            .show()


        binding.parentRecyclerView.layoutManager = LinearLayoutManager(root.context)

        parentAdapter = ParentAdapter()
        binding.parentRecyclerView.adapter = parentAdapter

        viewModel.parentItems.observe(viewLifecycleOwner) { parentItems ->
            parentAdapter.submitList(parentItems)
            binding.viewPager.adapter = WordPagerAdapter(parentItems)
        }

        binding.viewPager.setPageTransformer(false) { page, position ->
            val scale = if (position < -1 || position > 1) 0.75f else 1 - abs(position) * 0.25f
            page.scaleX = scale
            page.scaleY = scale
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}