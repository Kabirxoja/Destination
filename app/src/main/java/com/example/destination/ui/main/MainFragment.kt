package com.example.destination.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.destination.R
import com.example.destination.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


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




//        //Read Json file
//        val vocabularyList = parseJson(mainFragmentBinding.root.context)
//        vocabularyList?.forEach {
//            Log.d("Vocabulary", "theme: ${it.type} - unit: ${it.unit}  = ${it.english_word}")
//        }


        return mainFragmentBinding.root
    }

//    private fun getJsonDataFromAssets(context: Context, fileName: String): String? {
//        return try {
//            val inputStream = context.assets.open(fileName)
//            val size = inputStream.available()
//            val buffer = ByteArray(size)
//            inputStream.read(buffer)
//            inputStream.close()
//            String(buffer, Charsets.UTF_8)
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            null
//        }
//    }
//
//    private fun parseJson(context: Context): List<Vocabulary>? {
//        val jsonString = getJsonDataFromAssets(context, "main_data.json")
//        val gson = Gson()
//        val listType = object : TypeToken<List<Vocabulary>>() {}.type
//        return gson.fromJson(jsonString, listType)
//    }


}