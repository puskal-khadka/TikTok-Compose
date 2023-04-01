package com.puskal.home.tab.following

import androidx.lifecycle.viewModelScope
import com.puskal.core.base.BaseViewModel
import com.puskal.domain.following.GetContentCreatorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Puskal Khadka on 3/15/2023.
 */
@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val getContentCreatorsUseCase: GetContentCreatorsUseCase
) : BaseViewModel<ViewState, FollowingEvent>() {
    override fun onTriggerEvent(event: FollowingEvent) {
    }

    init {
        getContentCreator()
    }

    private fun getContentCreator() {
        viewModelScope.launch {
            getContentCreatorsUseCase().collect {
                updateState(ViewState(contentCreators = it))
            }
        }
    }
}