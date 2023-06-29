package example.imageviewer.model

import example.imageviewer.PlatformStorableImage
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readBytes
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class WildstagramPicture(
    val name: String,
    val imageUrl: String
)

class WildstagramService {
    private val httpClient = HttpClient()
    private val json = Json { ignoreUnknownKeys = true }
    private val apiKey : String = "4a452580-f4d7-4e34-9ce8-b70c8b18ab79"
    suspend fun getAll(): List<WildstagramPicture> {
        val response: HttpResponse = httpClient.get("https://wildstagram.nausicaa.wilders.dev/list") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $apiKey")
            }
        }

        val pictureNames: List<String> = json.decodeFromString(response.readBytes().decodeToString())

        return pictureNames.map { name ->
            WildstagramPicture(
                name = name,
                imageUrl = "https://wildstagram.nausicaa.wilders.dev/files/$name"
            )
        }
    }


    suspend fun pushImage(imageBitmap: PlatformStorableImage) {
        val response: HttpResponse = httpClient.post("https://wildstagram.nausicaa.wilders.dev/upload") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("description", "Wildstagram Image")
                        append("image", imageBitmap.toByteArray(), Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${generateRandomFileName()}"
                            )
                        })
                    },
                    boundary = "WebAppBoundary"
                )
            )
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }
        }
    }


    private fun generateRandomFileName(): String {
        // Générer un nom de fichier aléatoire ici
        return "random_file_name.jpg"
    }


}
