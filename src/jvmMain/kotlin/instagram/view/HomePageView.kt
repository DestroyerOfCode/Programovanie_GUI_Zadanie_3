package instagram.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
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

class HomePageView(private val homePageViewModel: HomePageViewModel) {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun init() {
        val profileView = ProfileView(ProfileViewModel(), homePageViewModel)
        val profileClicked = remember { mutableStateOf(-1) }

        if (profileClicked.value == -1)
            Column(modifier = Modifier.padding(Dp(10f)).background(Color.White).fillMaxHeight().fillMaxWidth()) {
                for (user in homePageViewModel.users.value) {
                    Row(modifier = Modifier.onClick { profileClicked.value = user.id }
                        .padding(bottom = Dp(5f))) {
                        Column {
                            homePageViewModel.AsyncImage(
                                load = {
                                    homePageViewModel.loadImageBitmap(
                                        user.profilePictureURL
                                            ?: "https://picsum.photos/id/${user.profilePictureNumber}/1024/968"
                                    )
                                },
                                painterFor = { remember { BitmapPainter(it) } },
                                contentDescription = "Profile Picture",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.width(50.dp)
                            )
                        }
                        Column(modifier = Modifier.padding(start = Dp(5f))) {
                            Text(text = user.name)
                            Text(text = user.description)
                        }
                    }
                    Divider(thickness = Dp(10f), modifier = Modifier.padding(bottom = Dp(5f)))
                }
            }
        else {
            profileView.init(homePageViewModel.users.value[profileClicked.value])
        }
    }
}