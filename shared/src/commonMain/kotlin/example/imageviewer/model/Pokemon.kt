package example.imageviewer.model

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Pokemon(
    val id: String,
    val name: String,
    val imageUrl: String
)

@Serializable
data class PokemonAPIResponse(
    val cards: List<Pokemon>
)

class PokemonService {
    private val httpClient = HttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getAll(): List<Pokemon> {
        val response: HttpResponse = httpClient.get("https://api.pokemontcg.io/v1/cards")
        val responseBody: String = response.bodyAsText()

        val apiResponse: PokemonAPIResponse = json.decodeFromString(responseBody)

        return apiResponse.cards
    }
}
