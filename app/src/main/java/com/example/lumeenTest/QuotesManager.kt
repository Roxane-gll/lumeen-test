package com.example.lumeenTest

import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object QuotesManager {
    /* Manager of quotes
    * Get quotes from API - at init or every time button is clicked
    * Get quote from id - at intent */

    val url = "https://dummyjson.com/quotes"
    private val client = OkHttpClient()
    var quotes:MutableList<Quote> = mutableListOf();

    init {
        // get quotes from API at start
        getQuotes()
    }
    
    fun getQuotes() {
        val request = Request.Builder()
            .url(url)
            .build()

        // call API to get quotes
        client.newCall(request).enqueue(object : Callback {
            // on fail to get API
            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }
            // on sucess to get API
            override fun onResponse(call: Call, response: Response) {
                var responseBody = response.body()?.string()

                // quotes get in list after changing to Java object
                var  quoteList = JSONObject(responseBody).getJSONArray("quotes")
                for (i in 0 until quoteList.length()) {
                    quotes.add(Gson().fromJson(quoteList[i].toString(), Quote::class.java))
                }
            }
        })
    }

    fun getQuoteFromId(id: Int): Quote? {
        /* Get quote from id
        * if found in list return quote (Java object) or return null */
        var quote = quotes.find {
            it.id.toInt() == id
        }
        return quote
    }
}
