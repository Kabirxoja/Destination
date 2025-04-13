package uz.kabirhoja.destination.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kabirhoja.destination.R
import com.kabirhoja.destination.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainFragment : Fragment() {

    private lateinit var mainFragmentBinding: FragmentMainBinding

    private lateinit var navHostFragment: Fragment

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainFragmentBinding = FragmentMainBinding.inflate(inflater, container, false)


        navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
        navController = navHostFragment.findNavController()

        val navView: BottomNavigationView = mainFragmentBinding.navView
        navView.setupWithNavController(navController)
        navView.itemIconTintList = null

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home,
                R.id.navigation_settings,
                R.id.navigation_notes,
                R.id.navigation_search,
                R.id.navigation_test_choice -> navView.visibility = View.VISIBLE
                else -> {
                    navView.visibility = View.GONE
                }
            }
        }

        return mainFragmentBinding.root
    }
}