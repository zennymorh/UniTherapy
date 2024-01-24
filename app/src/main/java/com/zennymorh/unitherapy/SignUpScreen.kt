package com.zennymorh.unitherapy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zennymorh.unitherapy.auth.EmailAddressTextField
import com.zennymorh.unitherapy.auth.PasswordTextField

@Composable
fun SignUpScreen(
    onSignUpWithEmail: () -> Unit,
    onNavigateToSignIn: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(30.dp))

        NameTextField()

        EmailAddressTextField()

        PasswordTextField()

        TherapistCheckbox(
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(90.dp))

        Button(
            shape = RoundedCornerShape(8.dp), onClick = { onSignUpWithEmail() }) {
            Text(
                text = stringResource(id = R.string.sign_up),
                fontSize = 18.sp,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Text(
            buildAnnotatedString {
                append("Already a user?")
                append(" ")
                withStyle(style = SpanStyle(color = colorResource(id = R.color.colorAccent))) {
                    append(stringResource(id = R.string.common_signin_button_text))
                }
            },
            fontSize = 12.sp,
            modifier = Modifier
                .padding(vertical = 14.dp)
                .clickable {
                    onNavigateToSignIn()
                },
        )

    }
}

@Composable
fun NameTextField() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun TherapistCheckbox(modifier: Modifier) {
    val checked = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { isChecked -> checked.value = isChecked }
        )
        Text(text = "I am a therapist")
    }
}

@Composable
@Preview
fun SignUpScreenPreview() {
    SignUpScreen(
        onSignUpWithEmail = {},
        onNavigateToSignIn = {}
    )
}