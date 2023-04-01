package com.puskal.commentlisting

import androidx.lifecycle.viewModelScope
import com.puskal.core.base.BaseViewModel
import com.puskal.domain.comment.GetCommentOnVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Puskal Khadka on 3/22/2023.
 */
@HiltViewModel
class CommentListViewModel @Inject constructor(
    private val getCommentOnVideoUseCase: GetCommentOnVideoUseCase
) : BaseViewModel<ViewState, CommentEvent>() {

    init {
        getContentCreator()
    }

    private fun getContentCreator() {
        viewModelScope.launch {
            getCommentOnVideoUseCase("vid").collect {
                updateState(ViewState(comments = it))
            }
        }
    }

    override fun onTriggerEvent(event: CommentEvent) {
    }


}