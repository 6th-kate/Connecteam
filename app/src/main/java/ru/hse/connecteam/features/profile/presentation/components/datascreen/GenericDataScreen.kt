package ru.hse.connecteam.features.profile.presentation.components.datascreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.features.profile.presentation.components.TransparentAppBar
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.images.AvatarPicker
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun GenericDataScreen(
    viewModel: GenericDataViewModel,
) {
    Scaffold(
        topBar = { TransparentAppBar(title = viewModel.screenTitle) }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 10.dp, bottom = 15.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                AvatarPicker(
                    image = viewModel.image,
                    onImageChosen = { viewModel.updateImage(it) },
                    onError = { viewModel.onImageError(it) })
                Spacer(modifier = Modifier.height(15.dp))
                BaseOutlinedTextInput(
                    text = viewModel.firstField,
                    onValueChanged = { viewModel.updateFirstField(it) },
                    label = viewModel.firstFieldLabel
                )
                BaseOutlinedTextInput(
                    text = viewModel.secondField,
                    onValueChanged = { viewModel.updateSecondField(it) },
                    label = viewModel.secondFieldLabel
                )
                BaseOutlinedTextInput(
                    text = viewModel.about,
                    onValueChanged = { viewModel.updateAbout(it) },
                    singleLine = false,
                    minLines = 10,
                    label = viewModel.aboutLabel
                )
            }
            GradientFilledButton(
                text = viewModel.saveButtonText,
                enabled = viewModel.saveEnabled,
                onClick = { viewModel.onSave() },
            )
        }
    }
}

@Preview
@Composable
fun GenericDataScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //GenericDataScreen(navController = rememberNavController())
        }
    }
}