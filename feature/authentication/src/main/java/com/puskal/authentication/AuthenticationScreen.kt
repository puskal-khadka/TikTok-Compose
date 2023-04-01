package com.puskal.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.puskal.composable.CustomIconButton
import com.puskal.core.AppContract.Annotate.ANNOTATED_PRIVACY_POLICY
import com.puskal.core.AppContract.Annotate.ANNOTATED_TAG
import com.puskal.core.AppContract.Annotate.ANNOTATED_TERMS_OF_SERVICE
import com.puskal.core.DestinationRoute.LOGIN_OR_SIGNUP_WITH_PHONE_EMAIL_ROUTE
import com.puskal.core.extension.Space
import com.puskal.theme.R
import com.puskal.theme.SubTextColor
import com.puskal.theme.fontFamily

/**
 * Created by Puskal Khadka on 3/27/2023.
 */
@Composable
fun AuthenticationScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.96f)
            .padding(horizontal = 28.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_cancel), contentDescription = null,
                modifier = Modifier
                    .clickable { navController.navigateUp() }
                    .size(24.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_question_circle),
                contentDescription = null
            )

        }

        56.dp.Space()
        Text(
            text = stringResource(id = R.string.login_or_sign_up),
            style = MaterialTheme.typography.displayMedium
        )
        20.dp.Space()
        Text(
            text = stringResource(id = R.string.login_in_to_your_existing_account),
            color = SubTextColor,
            textAlign = TextAlign.Center
        )
        28.dp.Space()
        AuthenticationButton {
            when (it) {
                LoginOption.PHONE_EMAIL_USERNAME -> navController.navigate(
                    LOGIN_OR_SIGNUP_WITH_PHONE_EMAIL_ROUTE
                )
                else -> {}
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        PrivacyPolicyFooter()
    }
}

@Composable
internal fun AuthenticationButton(onClickButton: (LoginOption) -> Unit) {
    LoginOption.values().asList().forEach {
        CustomIconButton(
            buttonText = stringResource(id = it.title),
            icon = it.icon,
            modifier = Modifier.fillMaxWidth(),
            containerColor = it.containerColor,
            contentColor = it.contentColor
        ) {
            onClickButton(it)
        }
        12.dp.Space()
    }
}

@Composable
fun PrivacyPolicyFooter() {
    val spanStyle = SpanStyle(
        fontSize = 13.sp,
        color = Color.Black,
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold
    )
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.by_continuing_you_agree).plus(" "))
        pushStringAnnotation(
            tag = ANNOTATED_TAG, annotation = ANNOTATED_TERMS_OF_SERVICE
        )
        withStyle(
            style = spanStyle
        ) {
            append(stringResource(id = R.string.terms_of_service))
        }
        pop()
        append(" ".plus(stringResource(id = R.string.and_acknowledge_that_you_have)).plus(" "))
        pushStringAnnotation(
            tag = ANNOTATED_TAG, annotation = ANNOTATED_PRIVACY_POLICY
        )
        withStyle(
            style = spanStyle
        ) {
            append(stringResource(id = R.string.privacy_policy))
        }
        pop()
        append(" ".plus(stringResource(id = R.string.to_learn_how_we_collect)))

    }

    ClickableText(
        text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = ANNOTATED_TAG, start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                when (annotation.item) {
                    ANNOTATED_TERMS_OF_SERVICE -> {

                    }
                    ANNOTATED_PRIVACY_POLICY -> {

                    }
                }
            }
        }, style = TextStyle(
            color = SubTextColor,
            fontFamily = fontFamily,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    )
    20.dp.Space()
}