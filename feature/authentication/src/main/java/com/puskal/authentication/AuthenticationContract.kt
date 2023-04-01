package com.puskal.authentication

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.puskal.theme.Black
import com.puskal.theme.PrimaryColor
import com.puskal.theme.R
import com.puskal.theme.White

/**
 * Created by Puskal Khadka on 3/27/2023.
 */
class AuthenticationContract {
}


enum class LoginOption(
    @DrawableRes var icon: Int,
    @StringRes val title: Int,
    val containerColor: Color = Color.Transparent,
    val contentColor: Color = Black
) {
    PHONE_EMAIL_USERNAME(
        icon = R.drawable.ic_profile,
        title = R.string.use_phone_email_username,
        containerColor = PrimaryColor,
        contentColor = White
    ),
    FACEBOOK(
        icon = R.drawable.ic_facebook,
        title = R.string.continue_with_facebook,
    ),
    GOOGLE(
        icon = R.drawable.ic_google,
        title = R.string.continue_with_google,
    ),
    TWITTER(
        icon = R.drawable.ic_twitter,
        title = R.string.continue_with_twitter,
    ),
}