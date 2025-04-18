package uz.kabirhoja.destination.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabirhoja.destination.R
import uz.kabirhoja.destination.data.data.HomeItem
import uz.kabirhoja.destination.data.repository.MainViewModelFactory
import com.kabirhoja.destination.databinding.FragmentHomeBinding
import uz.kabirhoja.destination.ui.adapters.HomeAdapter
import uz.kabirhoja.destination.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this, MainViewModelFactory(requireActivity().application))[HomeViewModel::class.java]

        homeViewModel.setLoadJson()

        homeAdapter = HomeAdapter()
        binding.topicsRecyclerView.adapter = homeAdapter
        binding.topicsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        homeAdapter.updateList(vocabularyTopicsList())

        homeAdapter.setOnClickItemListener(object : HomeAdapter.OnClickItemListener {
            override fun onClickItem(item: HomeItem) {
                val bundle = Bundle()
                bundle.putString("topicName", item.unitName)
                bundle.putInt("topicUnit", item.unitNumber)
                findNavController().navigate(
                    R.id.action_navigation_home_to_vocabularyFragment,
                    bundle
                )
            }
        })

    }

    private fun vocabularyTopicsList(): List<HomeItem> {
        return listOf(
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}