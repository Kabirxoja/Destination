package com.example.destination.ui.home.vocabulary

data class ParentItem(val enWord: String, val uzWord: String, val definition: String, val children: List<ChildItem>, var isExpanded: Boolean = false)