package instagram.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import instagram.model.HomePageViewModel
import instagram.model.ProfileViewModel
import instagram.model.User

class ProfileView(
    private val profileViewModel: ProfileViewModel,
    private val homePageViewModel: HomePageViewModel = HomePageViewModel(),
    private val homePageView: HomePageView = HomePageView(homePageViewModel),
) {
    @Composable
    fun init(user: User) {
        val homeClicked = remember { mutableStateOf(false) }

        if (homeClicked.value) {
            homePageView.init()
        } else {
            Column(modifier = Modifier.padding(Dp(10f)).background(Color.White).fillMaxHeight().fillMaxWidth()) {
                Row {
                    Button(onClick = { homeClicked.value = true }) {
                        Text(text = "Home")

                    }
                }
                Divider(thickness = Dp(10f))
                Row {
                    Text(user.name)
                    Spacer(modifier = Modifier.height(Dp(25f)).weight(10f, true))
                }
                Row {
                    homePageViewModel.AsyncImage(
                        load = {
                            homePageViewModel.loadImageBitmap(
                                "https://picsum.photos/id/${user.profilePictureNumber}/1024/968"
                            )
                        },
                        painterFor = { remember { BitmapPainter(it) } },
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.width(50.dp)
                    )
                    Column {
                        Text(user.name)
                        Text(user.description)
                    }
                }
            }
        }
    }
}