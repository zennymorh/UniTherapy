package com.zennymorh.unitherapy.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zennymorh.unitherapy.R

@Composable
fun SignInScreen(
    onSignInWithGoogle: () -> Unit,
    onSignInWithEmail: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Welcome back!",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedButton(
            shape = RoundedCornerShape(8.dp), onClick = { onSignInWithGoogle() }) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(18.dp),
                    tint = Color.Unspecified,
                )

                Text(
                    text = stringResource(id = R.string.log_in_with_google),
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                color = colorResource(id = R.color.colorAccent),
                thickness = 0.8.dp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = stringResource(id = R.string.log_in_with_email),
                fontSize = 12.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(14.dp)
            )

            Divider(
                color = colorResource(id = R.color.colorAccent),
                thickness = 0.8.dp,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        EmailAddressTextField()

        PasswordTextField()

        Text(
            text = stringResource(id = R.string.forgot_password),
            fontSize = 12.sp,
            modifier = Modifier
                .padding(vertical = 14.dp)
                .align(Alignment.End),
        )
        Spacer(modifier = Modifier.height(100.dp))

        Button(
            shape = RoundedCornerShape(8.dp), onClick = { onSignInWithEmail() }) {
            Text(
                text = stringResource(id = R.string.continue_text),
                fontSize = 18.sp,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Text(
            buildAnnotatedString {
                append(stringResource(id = R.string.first_time))
                append(" ")
                withStyle(style = SpanStyle(color = colorResource(id = R.color.colorAccent))) {
                    append(stringResource(id = R.string.sign_up_tv))
                }
            },
            fontSize = 12.sp,
            modifier = Modifier
                .padding(vertical = 14.dp),
        )

    }
}

@Composable
fun EmailAddressTextField() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Email Address") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordTextField() {
    var text by remember { mutableStateOf("") }

    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Password") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),

        trailingIcon = {
            val image = if (passwordVisibility)
                R.drawable.visibility
            else R.drawable.visibility_off
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(painter = painterResource(id = image), "")
            }
        },
    )
}

@Composable
@Preview
fun SignInScreenPreview() {
    SignInScreen(
        onSignInWithGoogle = {},
        onSignInWithEmail = {}
    )
}