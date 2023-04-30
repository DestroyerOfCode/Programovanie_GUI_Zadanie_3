package instagram.model

data class User(
    val id: Int,
    val profilePictureNumber: Int?,
    val name: String,
    val description: String,
    val profilePictureURL: String?
)