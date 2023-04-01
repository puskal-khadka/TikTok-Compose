package com.puskal.data.repository.creatorprofile

import com.puskal.data.model.UserModel
import com.puskal.data.model.VideoModel
import com.puskal.data.source.UsersDataSource.fetchSpecificUser
import com.puskal.data.source.VideoDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Puskal Khadka on 3/22/2023.
 */
class CreatorProfileRepository @Inject constructor() {
    fun getCreatorDetails(id: Long): Flow<UserModel?> {
        return fetchSpecificUser(id)
    }

    fun getCreatorPublicVideo(id: Long): Flow<List<VideoModel>> {
        return VideoDataSource.fetchVideosOfParticularUser(id)
    }
}