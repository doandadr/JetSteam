package com.doanda.jetsteam.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.doanda.jetsteam.R
import com.doanda.jetsteam.ui.theme.JetSteamTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(50.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.my_profile_photo),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )

        Text(
            text = stringResource(id = R.string.my_name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4
        )

        Text(
            text = stringResource(id = R.string.my_email_address),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1
        )

    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    JetSteamTheme() {
        AboutScreen()
    }
}