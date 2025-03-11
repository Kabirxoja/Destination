package com.example.destination.ui.home.vocabulary

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.destination.databinding.FragmentVocabularyBinding
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
        Toast.makeText(binding.root.context, "$topicUnit   - $topicNumber", Toast.LENGTH_SHORT).show()


        binding.parentRecyclerView.layoutManager = LinearLayoutManager(root.context)

        parentAdapter = ParentAdapter()
        binding.parentRecyclerView.adapter = parentAdapter

        viewModel.parentItems.observe(viewLifecycleOwner) { parentItems ->
            parentAdapter.submitList(parentItems)
            binding.viewPager.adapter = WordPagerAdapter(parentItems)
        }

        binding.viewPager.setPageTransformer(false) { page, position ->
            // Adjust the scale based on the position
            val scale = if (position < -1 || position > 1) 0.75f else 1 - abs(position) * 0.25f
            page.scaleX = scale
            page.scaleY = scale

            // Adjust the translation to ensure the next item's corner is visible
            val offset = position * page.width // Adjust the offset based on the page width
            page.translationX = -offset * 0.166f // Adjust the translation to control overlap
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}