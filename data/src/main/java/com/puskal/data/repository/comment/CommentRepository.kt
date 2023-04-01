package com.puskal.data.repository.comment

import com.puskal.data.model.CommentList
import com.puskal.data.source.CommentDataSource.fetchComment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Puskal Khadka on 3/22/2023.
 */
class CommentRepository @Inject constructor() {
    fun getComment(videoId: String): Flow<CommentList> {
        return fetchComment(videoId)
    }
}