package no.uia.ikt205.tictactoemu.api


import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.serialization.json.Json
import no.uia.ikt205.tictactoemu.ApplicationInterface
import no.uia.ikt205.tictactoemu.api.data.Game
import no.uia.ikt205.tictactoemu.api.data.GameState
import no.uia.ikt205.tictactoemu.R
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.serialization.decodeFromString


typealias GameServiceCallback = (state:Game?, errorCode:Int? ) -> Unit

object GameService {

    /// NOTE: Do not want to have App.context all over the code. Also it is nice if we later want to support different contexts
    private val context = ApplicationInterface.context

    /// NOTE: God practice to use a que for performing requests.
    private val requestQue:RequestQueue = Volley.newRequestQueue(context)

    /// NOTE: One posible way of constructing a list of API url. You want to construct the urls so that you can support different environments (i.e. Debug, Test, Prod etc)
    private enum class APIEndpoints(val url:String) {
        BaseURL("%1s%2s%3s".format(context.getString(R.string.protocol), context.getString(R.string.domain),context.getString(R.string.base_path))),

    }


    fun createGame(playerSpecifiedName:String, activeGameState:GameState, callback:GameServiceCallback) {

        val url = APIEndpoints.BaseURL.url

        val requestData = JSONObject()
        requestData.put("player", playerSpecifiedName)
        requestData.put("state",JSONArray(activeGameState))

        val request = object : JsonObjectRequest(
            Method.POST,url, requestData,
            {

                val requestActiveGameState = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())
                val requestActivePlayers = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                val requestActiveGameId:String = it.get("gameId").toString()
                val activeGameInstance = Game(requestActivePlayers, requestActiveGameId, requestActiveGameState)

                callback(activeGameInstance,null)
                Log.d("Game creation data:", activeGameInstance.toString())

            }, {
                // Error creating new game.
                callback(null, it.networkResponse.statusCode)
            } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.mainGameInterfaceKey)
                return headers
            }
        }

        requestQue.add(request)
    }

    fun joinGame(playerSpecifiedName: String, activeGameId: String, callback: GameServiceCallback) {

        /// https://generic-game-service.herokuapp.com/Game/5cxzg/join
        val url = APIEndpoints.BaseURL.url + "/" + activeGameId + context.getString(R.string.join_game_path)

        val requestData = JSONObject()
        requestData.put("player",playerSpecifiedName)
        requestData.put("gameId",activeGameId)


        val request = object : JsonObjectRequest(
            Method.POST,url, requestData,
            {

                val requestActiveGameState = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())
                val requestActivePlayers = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                val requestActiveGameId:String = it.get("gameId").toString()
                val activeGameInstance = Game(requestActivePlayers, requestActiveGameId, requestActiveGameState)

                callback(activeGameInstance,null)
                Log.d("Game join data:", activeGameInstance.toString())

            }, {
                // Error creating new game.
                callback(null, it.networkResponse.statusCode)
            } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.mainGameInterfaceKey)
                return headers
            }
        }

        requestQue.add(request)

    }

    // TODO: 12.05.2021  Check swagger what is needed polling and update
    fun updateGame(activeGameId: String,  activeGameState: GameState, callback: GameServiceCallback) {

        val url = APIEndpoints.BaseURL.url + "/" + activeGameId + context.getString(R.string.update_game_path)

        val requestData = JSONObject()
        requestData.put("gameId", activeGameId)
        requestData.put("state",JSONArray(activeGameState))

        val request = object : JsonObjectRequest(
            Method.POST,url, requestData,
            {

                val requestActiveGameState = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())
                val requestActivePlayers = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                val requestActiveGameId:String = it.get("gameId").toString()
                val activeGameInstance = Game(requestActivePlayers, requestActiveGameId, requestActiveGameState)

                callback(activeGameInstance,null)
                Log.d("Game update data:", activeGameInstance.toString())

            }, {
                // Error creating new game.
                callback(null, it.networkResponse.statusCode)
            } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.mainGameInterfaceKey)
                return headers
            }
        }

        requestQue.add(request)
    }



    fun pollGame(activeGameId: String, callback: GameServiceCallback) {

        val url = APIEndpoints.BaseURL.url + "/" + activeGameId + context.getString(R.string.poll_game_path)

        val requestData = JSONObject()

        val request = object : JsonObjectRequest(
            Method.GET,url, requestData,
            {

                val requestActiveGameState = Json.decodeFromString<List<MutableList<Char>>>(it.get("state").toString())
                val requestActivePlayers = Json.decodeFromString<MutableList<String>>(it.get("players").toString())
                val activeGameInstance = Game(requestActivePlayers, activeGameId, requestActiveGameState)

                callback(activeGameInstance,null)
                Log.d("Game polling data:", activeGameInstance.toString())

            }, {
                // Error creating new game.
                callback(null, it.networkResponse.statusCode)
            } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.mainGameInterfaceKey)
                return headers
            }
        }

        requestQue.add(request)

    }
}