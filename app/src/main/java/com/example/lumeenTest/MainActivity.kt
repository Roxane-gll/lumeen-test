package com.example.lumeenTest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lumeenTest.ui.theme.LumeenTheme

class MainActivity : ComponentActivity() {
    // Main Activity with display
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var quote = QuotesManager


        // get Intent ACTION_QUOTE
        var intent = intent
        if (intent.hasExtra("EXTRA_QUOTE_ID")){
            // get intent extra (quote id to get)
            var quote_id = intent.getIntExtra("EXTRA_QUOTE_ID", 0)
            var current_quote = quote.getQuoteFromId(quote_id)

            // send response by new Intent
            var new_intent = Intent("com.lumeen.inside.technique.QuoteBroadcastReceiver.ACTION_RESPONSE")
            // extra to send if quote has been found
            new_intent.putExtra("FOUND", current_quote != null)
            if (current_quote != null) {
                // if quote found - fill intent extras with id, quote and author of found quote
                new_intent.putExtra("ID", current_quote.id)
                new_intent.putExtra("CITATION", current_quote.quote)
                new_intent.putExtra("AUTHOR", current_quote.author)
            }
            // send intent
            if (new_intent.resolveActivity(packageManager) != null) {
                startActivity(new_intent)
            }
        }

        setContent {
            LumeenTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Column() {
                            Button(onClick = {
                                // Get new quotes
                                quote.getQuotes()
                            }) {
                                Text(text = "Quotes")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var quote = QuotesManager

    LumeenTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column() {
                Button(onClick = {
                    quote.getQuotes()
                }) {
                    Text(text = "Bonjour")
                }
            }
        }
    }
}