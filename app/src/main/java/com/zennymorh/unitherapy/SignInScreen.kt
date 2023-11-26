package com.zennymorh.unitherapy

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
internal fun SignInScreen(onClick: () -> Unit) {
    Column {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 30.sp
        )

        ElevatedButton(onClick = { onClick() }) {
            Text(text = stringResource(R.string.log_in))
        }
    }

}

