package instagram.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.net.URL

class HomePageViewModel() {

    val users: MutableState<List<User>>

    init {
        val gson = Gson()
        val users = File("src/jvmMain/resources/users.json").readText()
        val userListType = object : TypeToken<List<User>>() {}.type
        this.users = mutableStateOf(gson.fromJson(users, userListType))
    }

    @Composable
    fun <T> AsyncImage(
        painterFor: @Composable (T) -> Painter,
        contentDescription: String,
        modifier: Modifier = Modifier,
        contentScale: ContentScale = ContentScale.Fit,
        load: suspend () -> T,
    ) {
        val image: T? by produceState<T?>(null) {
            value = withContext(Dispatchers.IO) {
                try {
                    load()
                } catch (e: IOException) {
                    null
                }
            }
        }

        if (image != null) {
            RoundedImage(
                painter = painterFor(image!!),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = modifier
            )
        }
    }
    @Composable
    fun RoundedImage(
        painter: Painter,
        contentDescription: String?,
        contentScale: ContentScale = ContentScale.Crop,
        modifier: Modifier = Modifier,
        imageSize: Int = 50
    ) {
        Box(
            modifier = modifier
                .size(imageSize.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier
                    .size(imageSize.dp)
                    .clip(CircleShape)
            )
        }
    }

    fun loadImageBitmap(url: String): ImageBitmap =
        URL(url).openStream().buffered().use { androidx.compose.ui.res.loadImageBitmap(it) }
}