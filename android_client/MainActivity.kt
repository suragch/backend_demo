package com.example.androidclient

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import android.util.Log
import android.view.View
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

private const val HOST = "http://10.0.2.2:3000"
private const val TAG = "TAG"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun makeGetAllRequest(view: View) {
        HttpGetRequest().execute(HOST)
    }

    fun makeGetOneRequest(view: View) {
        val idToGet = 0
        val url = "$HOST/$idToGet"
        HttpGetRequest().execute(url)
    }

    fun makePostRequest(view: View) {
        val json = "{\"fruit\": \"pear\", \"color\": \"green\"}"
        HttpPostRequest().execute(HOST, json)
    }

    fun makePutRequest(view: View) {
        val idToReplace = 0
        val url = "$HOST/$idToReplace"
        val json = "{\"fruit\": \"watermellon\", \"color\": \"red and green\"}"
        HttpPutRequest().execute(url, json)
    }

    fun makePatchRequest(view: View) {
        val idToUpdate = 0
        val url = "$HOST/$idToUpdate"
        val json = "{\"color\": \"green\"}"
        HttpPatchRequest().execute(url, json)
    }

    fun makeDeleteRequest(view: View) {
        val idToDelete = 0
        val url = "$HOST/$idToDelete"
        HttpDeleteRequest().execute(url)
    }

    class HttpGetRequest : AsyncTask<String, Void, Void>() {
        override fun doInBackground(vararg params: String): Void? {
            val urlString = params[0]

            val myUrl = URL(urlString)
            val connection = myUrl.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val result = getStringFromInputStream(connection.inputStream)
            val statusCode = connection.responseCode
            connection.disconnect()

            Log.i(TAG, "GET result: $statusCode $result")
            return null
        }
    }

    class HttpPostRequest : AsyncTask<String, Void, Void>() {
        override fun doInBackground(vararg params: String): Void? {
            val urlString = params[0]
            val json = params[1]

            val myUrl = URL(urlString)
            val connection = myUrl.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")

            writeStringToOutputStream(json, connection.outputStream)
            val result = getStringFromInputStream(connection.inputStream)
            val statusCode = connection.responseCode
            connection.disconnect()

            Log.i(TAG, "POST result: $statusCode $result")
            return null
        }
    }

    class HttpPutRequest : AsyncTask<String, Void, Void>() {
        override fun doInBackground(vararg params: String): Void? {
            val urlString = params[0]
            val json = params[1]

            val myUrl = URL(urlString)
            val connection = myUrl.openConnection() as HttpURLConnection
            connection.requestMethod = "PUT"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")

            writeStringToOutputStream(json, connection.outputStream)
            val result = getStringFromInputStream(connection.inputStream)
            val statusCode = connection.responseCode
            connection.disconnect()

            Log.i(TAG, "PUT result: $statusCode $result")
            return null
        }
    }

    class HttpPatchRequest : AsyncTask<String, Void, Void>() {
        override fun doInBackground(vararg params: String): Void? {
            val urlString = params[0]
            val json = params[1]

            val myUrl = URL(urlString)
            val connection = myUrl.openConnection() as HttpURLConnection
            connection.requestMethod = "PATCH"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json")

            writeStringToOutputStream(json, connection.outputStream)
            val result = getStringFromInputStream(connection.inputStream)
            val statusCode = connection.responseCode
            connection.disconnect()

            Log.i(TAG, "PATCH result: $statusCode $result")

            return null
        }
    }


    class HttpDeleteRequest : AsyncTask<String, Void, Void>() {
        override fun doInBackground(vararg params: String): Void? {
            val urlString = params[0]

            val myUrl = URL(urlString)
            val connection = myUrl.openConnection() as HttpURLConnection
            connection.requestMethod = "DELETE"

            val result = getStringFromInputStream(connection.inputStream)
            val statusCode = connection.responseCode
            connection.disconnect()

            Log.i(TAG, "DELETE result: $statusCode $result")
            return null
        }
    }

}


private fun writeStringToOutputStream(json: String, outputStream: OutputStream) {
    val bytes = json.toByteArray(charset("UTF-8")) // API 19: StandardCharsets.UTF_8
    outputStream.write(bytes)
    outputStream.close()
}

private fun getStringFromInputStream(stream: InputStream): String {
    val text =  stream.bufferedReader().use { it.readText() }
    stream.close()
    return text
}
