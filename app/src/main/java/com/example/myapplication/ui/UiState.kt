package com.example.myapplication.ui

import com.example.myapplication.data.Post

data class UiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val isLastPage: Boolean = false,
    val error: String? = null,
)
