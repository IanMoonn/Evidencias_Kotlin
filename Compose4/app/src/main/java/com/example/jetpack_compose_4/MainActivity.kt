package com.example.jetpack_compose_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack_compose_4.ui.theme.Jetpack_Compose_4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpack_Compose_4Theme {
                // A surface container using the 'background' color from the theme
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    //add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.sus),
            contentDescription = null,
            modifier = Modifier
                // Set image size to 40.dp
                .size(40.dp)
                //Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
        )
        
        //add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        //If the message is expanded or not in this variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be update gradually form one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        //We toggle the isExpanded variable when we click on this column
        Column (modifier = Modifier.clickable { isExpanded = !isExpanded }){
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )
            // add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(4.dp),
                    //If the message is expanded, we display all its content
                    //otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn{
        items(messages) {message ->
            MessageCard(message)
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

@Preview
@Composable
fun PreviewConversation(){
    Jetpack_Compose_4Theme {
        Conversation(SampleData.conversationSample)
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    Jetpack_Compose_4Theme {
        Surface{
            MessageCard(
                msg = Message("Among Us", "Hey, take a look at Jetpack Compose, it's great!")
            )
        }
    }
}

object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Among Us",
            "Test...Test...Test..."
        ),
        Message(
            "Among Us",
            "List of Android versions:\n" +
                    "Android KitKat (API 19)\n" +
                    "Android Lollipop (API 21)\n" +
                    "Android Marshmallow (API 23)\n" +
                    "Android Nougat (API 24)\n" +
                    "Android Oreo (API 26)\n" +
                    "Android Pie (API 28)\n" +
                    "Android 10 (API 29)\n" +
                    "Android 11 (API 30)\n" +
                    "Android 12 (API 31)\n"
        ),
        Message(
            "Among Us",
            "I think Kotlin is my favorite programming language.\n" +
                    "It's so much fun!"
        ),
        Message(
            "Among Us",
            "Searching for alternatives to XML layouts..."
        ),
        Message(
            "Among Us",
            "Hey, take a look at Jetpack Compose, it's great!\n" +
                    "It's the Android's modern toolkit for building native UI." +
                    "It simplifies and accelerates UI development on Android." +
                    "Less code, powerful tools, and intuitive Kotlin APIs :)"
        ),
        Message(
            "Among Us",
            "It's available from API 21+ :)"
        ),
        Message(
            "Among Us",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Among Us",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Among Us",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Among Us",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Among Us",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Among Us",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Among Us",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}