package uz.kabirhoja.destination.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uz.kabirhoja.destination.data.repository.MainViewModelFactory
import com.kabirhoja.destination.databinding.FragmentResultBinding
import uz.kabirhoja.destination.viewmodel.ResultViewModel


class ResultFragment : Fragment() {

    private var _viewBinding: FragmentResultBinding? = null
    private val binding get() = _viewBinding!!
    private lateinit var resultViewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentResultBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultViewModel = ViewModelProvider(this, MainViewModelFactory(requireActivity().application))[ResultViewModel::class.java]

        val initialSize = arguments?.getInt("listSize") ?: 0

        binding.numberOfWords.text = initialSize.toString()

        binding.backToMenu.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}