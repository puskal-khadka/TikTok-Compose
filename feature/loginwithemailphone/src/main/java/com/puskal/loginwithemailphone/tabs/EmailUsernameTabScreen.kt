package com.puskal.loginwithemailphone.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.puskal.composable.CustomButton
import com.puskal.core.AppContract
import com.puskal.core.extension.Space
import com.puskal.loginwithemailphone.LoginEmailPhoneEvent
import com.puskal.loginwithemailphone.LoginWithEmailPhoneViewModel
import com.puskal.loginwithemailphone.suggestedDomainList
import com.puskal.theme.*
import com.puskal.theme.R

/**
 * Created by Puskal Khadka on 3/27/2023.
 */

@Composable
fun EmailUsernameTabScreen(viewModel: LoginWithEmailPhoneViewModel) {
    val email by viewModel.email.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 42.dp, start = 28.dp, end = 28.dp)
                .weight(1f),
        ) {
            EmailField(email, viewModel)
            8.dp.Space()
            PrivacyPolicyText {}
            16.dp.Space()
            CustomButton(
                buttonText = stringResource(id = R.string.next),
                modifier = Modifier.fillMaxWidth(),
                isEnabled = email.first.isNotEmpty()
            ) {

            }
        }

        DomainSuggestion {
            viewModel.onTriggerEvent(
                LoginEmailPhoneEvent.OnChangeEmailEntry(
                    "${email.first.substringBefore("@")}$it"
                )
            )
        }
        16.dp.Space()

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(email: Pair<String, String?>, viewModel: LoginWithEmailPhoneViewModel) {
    val currentPage by viewModel.settledPage.collectAsState()
    val focusRequester = remember { FocusRequester() }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = email.first,
        textStyle = MaterialTheme.typography.labelLarge,
        onValueChange = { viewModel.onTriggerEvent(LoginEmailPhoneEvent.OnChangeEmailEntry(it)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = SubTextColor,
            unfocusedIndicatorColor = SubTextColor,
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.enter_email_address_or_username),
                style = MaterialTheme.typography.labelLarge,
                color = SubTextColor
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                viewModel.onTriggerEvent(
                    LoginEmailPhoneEvent.OnChangeEmailEntry(
                        ""
                    )
                )
            }) {
                if (email.first.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = null
                    )
                }

            }
        }
    )
    LaunchedEffect(key1 = currentPage) {
        if (currentPage == 1) {
            focusRequester.requestFocus()
        }
    }
}


@Composable
fun PrivacyPolicyText(onClickAnnotation: (String) -> Unit) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.by_continuing_you_agree))

        pushStringAnnotation(
            tag = AppContract.Annotate.ANNOTATED_TAG,
            annotation = AppContract.Annotate.ANNOTATED_TERMS_OF_SERVICE
        )
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        ) { append(" ${stringResource(id = R.string.terms_of_service)}") }
        pop()

        append(stringResource(id = R.string.and_confirm_that_you_have_read))

        pushStringAnnotation(
            tag = AppContract.Annotate.ANNOTATED_TAG,
            annotation = AppContract.Annotate.ANNOTATED_PRIVACY_POLICY
        )
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        ) { append(" ${stringResource(id = R.string.privacy_policy)}") }
        pop()
    }

    ClickableText(
        text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = AppContract.Annotate.ANNOTATED_TAG, start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                onClickAnnotation(annotation.item)
            }
        }, style = TextStyle(
            fontFamily = fontFamily
        )
    )
}

@Composable
fun DomainSuggestion(onClickDomain: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(suggestedDomainList) { domain ->
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(2.dp),
                        color = SeparatorColor
                    )
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                ClickableText(
                    text = AnnotatedString(domain),
                    style = MaterialTheme.typography.titleSmall,
                    onClick = {
                        onClickDomain(domain)
                    })
            }
        }
    }
}
