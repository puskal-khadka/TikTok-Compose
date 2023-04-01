package com.puskal.data.repository.home

import com.puskal.data.model.ContentCreatorFollowingModel
import com.puskal.data.source.ContentCreatorForFollowingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Puskal Khadka on 3/15/2023.
 */
class FollowingRepository @Inject constructor() {
    fun getContentCreatorForFollowing(): Flow<List<ContentCreatorFollowingModel>> {
        return ContentCreatorForFollowingDataSource.fetchContentCreatorForFollowing()
    }
}