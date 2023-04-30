package instagram

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import instagram.model.HomePageViewModel
import instagram.view.HomePageView

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Instagram Wheey") {
        val homePageViewModel = HomePageViewModel()
        val homePageView = HomePageView(homePageViewModel)

        homePageView.init()
    }
}
