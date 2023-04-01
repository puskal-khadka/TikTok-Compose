package com.puskal.loginwithemailphone.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.puskal.composable.CustomButton
import com.puskal.core.AppContract
import com.puskal.core.extension.Space
import com.puskal.loginwithemailphone.LoginEmailPhoneEvent
import com.puskal.loginwithemailphone.LoginWithEmailPhoneViewModel
import com.puskal.theme.R
import com.puskal.theme.SeparatorColor
import com.puskal.theme.SubTextColor
import com.puskal.theme.fontFamily

/**
 * Created by Puskal Khadka on 3/27/2023.
 */
@Composable
fun PhoneTabScreen(viewModel: LoginWithEmailPhoneViewModel) {
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val dialCode by viewModel.dialCode.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp)
            .padding(top = 52.dp)
    ) {
        PhoneNumberField(viewModel, phoneNumber, dialCode)
        8.dp.Space()
        InfoAnnotatedText {
        }
        16.dp.Space()
        CustomButton(
            buttonText = stringResource(id = R.string.send_code),
            modifier = Modifier.fillMaxWidth(),
            isEnabled = phoneNumber.first.isNotEmpty()
        ) {

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhoneNumberField(
    viewModel: LoginWithEmailPhoneViewModel,
    phoneNumber: Pair<String, String?>,
    dialCode: Pair<String, String?>
) {
    val currentPage by viewModel.settledPage.collectAsState()

    val focusRequester = remember { FocusRequester() }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = phoneNumber.first,
        onValueChange = {
            viewModel.onTriggerEvent(LoginEmailPhoneEvent.OnChangePhoneNumber(it))
        },
        textStyle = MaterialTheme.typography.labelLarge,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = SubTextColor,
            unfocusedIndicatorColor = SubTextColor,
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.phone_number),
                style = MaterialTheme.typography.labelLarge
            )
        },
        leadingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = dialCode.first)  //static for now
                Icon(
                    painter = painterResource(id = R.drawable.ic_down_more),
                    contentDescription = null,
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(12.dp)
                        .background(color = SeparatorColor)
                )

            }
        },
        trailingIcon = {
            IconButton(onClick = {
                viewModel.onTriggerEvent(
                    LoginEmailPhoneEvent.OnChangePhoneNumber(
                        ""
                    )
                )
            }) {
                if (phoneNumber.first.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = null
                    )
                }

            }
        }
    )
    LaunchedEffect(key1 = currentPage) {
        if (currentPage == 0) {
            focusRequester.requestFocus()
        }
    }
}


@Composable
fun InfoAnnotatedText(onClickLearnMore: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.phone_number_info))
        pushStringAnnotation(
            tag = AppContract.Annotate.ANNOTATED_TAG,
            annotation = AppContract.Annotate.ANNOTATED_LEARN_MORE
        )
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        ) { append(" ${stringResource(id = R.string.learn_more)}") }
        pop()
    }

    ClickableText(
        text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = AppContract.Annotate.ANNOTATED_TAG, start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                if (annotation.item == AppContract.Annotate.ANNOTATED_LEARN_MORE) {
                    onClickLearnMore()
                }
            }
        }, style = TextStyle(
            color = SubTextColor,
            fontFamily = fontFamily
        )
    )
}

