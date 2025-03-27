package com.example.destination.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.destination.R
import com.example.destination.databinding.FragmentVocabularyBinding
import com.example.destination.ui.adapters.VocabularyAdapter
import com.example.destination.ui.adapters.WordPagerAdapter
import com.example.destination.viewmodel.VocabularyViewModel
import kotlin.math.abs


class VocabularyFragment : Fragment() {
    private var _binding: FragmentVocabularyBinding? = null
    private val binding get() = _binding!!

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
        val topicUnit = args?.getString("topicName")
        val topicNumber = args?.getInt("topicUnit")
        Toast.makeText(binding.root.context, "$topicUnit   - $topicNumber", Toast.LENGTH_SHORT).show()

        val vocabularyAdapter = VocabularyAdapter()

        binding.parentRecyclerView.adapter = vocabularyAdapter
        binding.parentRecyclerView.layoutManager = LinearLayoutManager(root.context)

        val numToStr = topicNumber.toString()
        viewModel.getWordsByUnit(numToStr)

        viewModel.wordsByUnit.observe(viewLifecycleOwner) { words ->
            //List
            binding.parentRecyclerView.adapter = vocabularyAdapter
            vocabularyAdapter.submitList(words)

            //ViewPager
            val wordPagerAdapter = WordPagerAdapter(words)
            binding.viewPager.adapter = wordPagerAdapter
        }





        binding.btnChange.setOnClickListener {
            if (binding.viewPager.visibility.equals(View.GONE)) {
                binding.viewPager.visibility = View.VISIBLE
                binding.parentRecyclerView.visibility = View.GONE
                binding.btnChangeIcon.background = resources.getDrawable(R.drawable.ic_list)
            } else {
                binding.viewPager.visibility = View.GONE
                binding.parentRecyclerView.visibility = View.VISIBLE
                binding.btnChangeIcon.background = resources.getDrawable(R.drawable.ic_card)
            }
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


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
