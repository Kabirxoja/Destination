package com.example.destination.ui.home.vocabulary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VocabularyViewModel : ViewModel() {
    private val _parentItems = MutableLiveData<List<ParentItem>>()
    val parentItems: LiveData<List<ParentItem>> = _parentItems

    init {
        val sampleData = listOf(
            ParentItem(
                "beat (v)",
                "yengmoq",
                "To defeat someone in a game, competition, election, or battle",
                listOf(
                    ChildItem(
                        "England needed to beat Germany to get to the final.",
                        "Angliya finalga chiqish uchun Germaniyani mag'lub etishi kerak edi."
                    )
                )
            ),
            ParentItem(
                "board game (n)",
                "doska oyini",
                "Any game in which you move objects around on a special board",
                listOf(
                    ChildItem(
                        "I think that Trivial Pursuit is my favourite board game.",
                        "Menimcha, Trivial Pursuit mening eng sevimli stol o'yinim."
                    )
                )
            ),
            ParentItem(
                "captain (n)",
                "sardor (kapitan)",
                "The person who is in charge of a team or organisation",
                listOf(
                    ChildItem(
                        "She was captain of the Olympic swimming team.",
                        "U Olimpiada suzish guruhining sardori edi."
                    )
                )
            ),
        )
        _parentItems.value = sampleData
    }
}


