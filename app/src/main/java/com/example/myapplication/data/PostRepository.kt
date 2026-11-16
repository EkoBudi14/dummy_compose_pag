package com.example.myapplication.data

class PostRepository(private val postApi: PostApi) {

    suspend fun getPosts(page: Int, limit: Int): Result<List<Post>> {
        return try {
            Result.success(postApi.getPosts(page, limit))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
