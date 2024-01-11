package com.example.thegoodplaceapp

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import java.net.URL

class GpsLocation {
    companion object {
        @JvmStatic
        suspend fun getCityName(): String {

//            val url = URL("https://maps.googleapis.com/maps/api/geocode/json").openStream().bufferedReader().use { it.readText() }
//            val client = HttpClient(CIO)
//            val response: HttpResponse = client.get("https://ktor.io/")
//            println(response.status)
//            client.close()
            return "בלהבלה"
        }
    }
}
