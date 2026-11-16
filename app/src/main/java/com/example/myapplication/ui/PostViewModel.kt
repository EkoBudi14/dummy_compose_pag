package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ApiCall
import com.example.myapplication.data.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {

    private val postRepository = PostRepository(ApiCall.postApi)

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private var page = 1
    private val limit = 10

    init {
        loadNext()
    }

    fun loadNext() {
        if (_state.value.isLoading || _state.value.isLastPage) return
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            postRepository.getPosts(page, limit)
                .onSuccess { newPosts ->
                    page++
                    _state.update {
                        it.copy(
                            posts = it.posts + newPosts,
                            isLoading = false,
                            isLastPage = newPosts.size < limit,
                        )
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Error get posts") }
                }
        }
    }
}
