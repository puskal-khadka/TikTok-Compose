package com.puskal.commentlisting

import com.puskal.data.model.CommentList

/**
 * Created by Puskal Khadka on 3/22/2023.
 */
data class ViewState(
    val isLoading: Boolean? = null,
    val error: String? = null,
    val comments: CommentList? = null
)

sealed class CommentEvent {
}

enum class HighlightedEmoji(val unicode: String = "") {
    LAUGHING_WITH_SMILING_EYES(unicode = "\uD83D\uDE01"),
    SMILING_WITH_HEART(unicode = "\uD83E\uDD70"),
    TEAR_OF_JOY(unicode = "\uD83D\uDE02"),
    FLUSHED_FACE(unicode = "\uD83D\uDE33"),
    SMIRKING_FACE(unicode = "\uD83D\uDE0F"),
    GRINNING_FACE(unicode = "\uD83D\uDE05"),
}