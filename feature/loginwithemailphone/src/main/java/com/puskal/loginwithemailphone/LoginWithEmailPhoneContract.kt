package com.puskal.loginwithemailphone

import androidx.annotation.StringRes
import com.puskal.theme.R

/**
 * Created by Puskal Khadka on 3/27/2023.
 */
data class ViewState(
    val isLoading: Boolean? = null,
    val error: String? = null,
)

sealed class LoginEmailPhoneEvent {
    data class EventPageChange(val settledPage: Int) : LoginEmailPhoneEvent()
    data class OnChangePhoneNumber(val newValue: String) : LoginEmailPhoneEvent()
    data class OnChangeEmailEntry(val newValue: String) : LoginEmailPhoneEvent()
}


enum class LoginPages(@StringRes val title: Int) {
    PHONE(title = R.string.phone),
    EMAIL_USERNAME(title = R.string.email_username)
}

val suggestedDomainList =
    arrayListOf("@gmail.com", "@hotmail.com", "@outlook.com", "@yahoo.com", "@icloud.com")