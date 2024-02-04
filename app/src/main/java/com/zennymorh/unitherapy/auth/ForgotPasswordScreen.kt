package com.zennymorh.unitherapy.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zennymorh.unitherapy.R


@Composable
fun ForgotPasswordScreen(
    onSubmitClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        var text by remember { mutableStateOf("") }

        Text(
            text = stringResource(id = R.string.forgot_password),
            style = MaterialTheme.typography.headlineSmall,
        )
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 48.dp)
        )
        Text(
            text = stringResource(id = R.string.reset_password_desc),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )

        Button(
            shape = RoundedCornerShape(8.dp), onClick = { onSubmitClicked() }) {
            Text(
                text = stringResource(id = R.string.submit),
                fontSize = 18.sp,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }

}

@Preview
@Composable
fun ForgotPasswordPreview() {
    ForgotPasswordScreen(
        onSubmitClicked = {}
    )
}