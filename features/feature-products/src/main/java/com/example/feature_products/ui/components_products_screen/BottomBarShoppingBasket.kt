package com.example.feature_products.ui.components_products_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_products.models.StateBasket
import com.example.feature_products.ui.components.LabelNotifications

@Composable
fun BottomBarShoppingBasket(modifier: Modifier, stateBasket: State<StateBasket?>) {
    BottomAppBar(modifier, contentColor = Color.Transparent, containerColor = Color.White) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val modifierButton = Modifier.width(170.dp)
            Box(modifier = Modifier.fillMaxHeight()) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = modifierButton.align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray.copy(0.5f)
                    ),
                    shape = RoundedCornerShape(15.dp),
                    elevation = null
                ) {
                    Text(
                        text = "Archive",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = 24.sp
                    )
                }
                stateBasket.value?.let {
                    if (it.countProducts != 0) {
                        LabelNotifications(
                            text = it.countProducts.toString(),
                            modifier = Modifier
                                .align(Alignment.TopEnd).offset(y=5.dp)
                        )
                    }
                }
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = modifierButton,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(237, 170, 75)
                ),
                shape = RoundedCornerShape(15.dp),
                elevation = null
            ) {
                Text(
                    text = "In progress",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 24.sp
                )

            }
        }
    }
}
