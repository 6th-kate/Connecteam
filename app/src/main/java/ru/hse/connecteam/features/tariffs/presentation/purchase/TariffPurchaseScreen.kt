package ru.hse.connecteam.features.tariffs.presentation.purchase

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import ru.hse.connecteam.R
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.OutlinedGradientButton
import ru.hse.connecteam.ui.components.modals.SelfHidingBottomAlert
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.BigGradientLabel
import ru.hse.connecteam.ui.theme.BigWhiteLabel37
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.OTPInput
import ru.hse.connecteam.ui.theme.SpanButtonWhiteLabel

@Composable
fun TariffPurchaseScreen(
    viewModel: TariffPurchaseViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 0.dp, vertical = 20.dp)
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(viewModel.shouldReturn) {
            if (viewModel.shouldReturn) {
                delay(2000)
                viewModel.stopAlert()
                navController.popBackStack()
                navController.popBackStack()
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(horizontal = 136.dp, vertical = 15.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo"
            )
            Text(
                viewModel.tariffInfo.tariffName,
                style = OTPInput.copy(fontSize = 26.sp),
                textAlign = TextAlign.Center
            )
            Text(
                viewModel.tariffInfo.possibilities,
                style = SpanButtonWhiteLabel.copy(lineHeight = 22.sp),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(horizontal = 40.dp)
            )
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        brush = BaseGradientBrush,
                        shape = CircleShape,
                    )
                    .padding(horizontal = 50.dp, vertical = 15.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        viewModel.tariffInfo.standardCost.toString(),
                        style = BigWhiteLabel37,
                    )
                    Text("₽", style = BigGradientLabel)
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GradientFilledButton(
                text = viewModel.purchaseButtonText,
                enabled = viewModel.purchaseButtonEnabled,
                onClick = { viewModel.purchase() })
            OutlinedGradientButton(
                text = "Отмена",
                onClick = { navController.popBackStack() },
            )
        }
        if (viewModel.shouldShowAlert) {
            SelfHidingBottomAlert(viewModel.alertText)
        }
    }
}

@Preview
@Composable
fun TariffPurchaseScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            TariffPurchaseScreen(
                navController = rememberNavController()
            )
        }
    }
}