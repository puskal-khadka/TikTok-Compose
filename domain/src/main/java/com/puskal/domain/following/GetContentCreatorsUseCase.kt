package com.puskal.domain.following

import com.puskal.data.model.ContentCreatorFollowingModel
import com.puskal.data.repository.home.FollowingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Puskal Khadka on 3/15/2023.
 */
class GetContentCreatorsUseCase @Inject constructor(private val followingRepository: FollowingRepository) {
    operator fun invoke(): Flow<List<ContentCreatorFollowingModel>> {
        return followingRepository.getContentCreatorForFollowing()
    }
}