Here’s the updated tutorial without the GitHub section:

---

## Retrofit and Jetpack Compose: Simple User Fetching Application

### Description
This application fetches a list of users from the [Reqres](https://reqres.in/) API using **Retrofit** and displays them in a list using **Jetpack Compose**. A button triggers the API call, and the data is displayed in a lazy column. Loading indicators show the progress during the API call.

---

### Features
1. Fetches user data from `https://reqres.in/api/users`.
2. Displays a list of users in a **LazyColumn**.
3. Shows a loading indicator during the API request.

---

### 1. **Setup the Project**

#### Dependencies
Add the following dependencies in your `build.gradle` file:

```kotlin
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.google.code.gson:gson:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
```

---

### 2. **Create the Data Models**

Create a data model to represent the API response and user data.

```kotlin
data class GetUserResponse(
    val data: List<User>
)

data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
)
```

---

### 3. **Define the API Interface**

Create an interface `Api` to define the endpoint for fetching users.

```kotlin
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("users")
    suspend fun getUsers(): Response<GetUserResponse>
}
```

---

### 4. **Build the Retrofit Instance and Compose UI**

The UI and network call logic are in the `MainActivity`:

```kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sy.network_connection.ui.theme.Network_connectionTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(Api::class.java)

        // Set Compose UI
        setContent {
            Network_connectionTheme {
                val coroutineScope = rememberCoroutineScope()
                val users = remember { mutableStateListOf<User>() }
                val isLoading = remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                isLoading.value = true
                                val usersResponse = api.getUsers().body()
                                usersResponse?.let {
                                    users.clear()
                                    users.addAll(it.data)
                                }
                                isLoading.value = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isLoading.value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text(text = "Get Users")
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(items = users) { user ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "${user.firstName} ${user.lastName}")
                            }
                        }
                    }
                }
            }
        }
    }
}
```

---

### 5. **Run the Application**
- Make sure your Android project has Internet permission. Add this to the `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

- Build and run the app. Press the "Get Users" button to fetch and display the user list.

---

Let me know if you need further assistance!
