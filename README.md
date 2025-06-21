# Weather App 🌤️

A beautiful Android weather application built with Kotlin and XML that provides current weather information and 5-day forecasts with automatic location detection.

## 📱 Features

- **Automatic Location Detection**: Uses GPS to fetch weather data for your current location
- **Current Weather Display**: Shows temperature, weather description, min/max temperatures, and AQI
- **City Search**: Search for weather information in any city worldwide
- **5-Day Forecast**: Detailed forecast with day, date, and temperature ranges in a RecyclerView
- **Beautiful UI**: Gradient background design matching modern weather app aesthetics
- **Real-time Data**: Powered by OpenWeatherMap API

## 🛠️ Technologies Used

- **Language**: Kotlin
- **UI**: XML Layouts
- **Architecture**: MVVM pattern with LiveData
- **Networking**: Retrofit2 + Gson
- **Location Services**: Google Play Services Location API
- **API**: OpenWeatherMap API
- **Coroutines**: For asynchronous operations
- **RecyclerView**: For forecast list display

## 📋 Prerequisites

- Android Studio Arctic Fox or newer
- Android SDK 24 (Android 7.0) or higher
- Google Play Services
- OpenWeatherMap API Key

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/weather-app.git
cd weather-app
```

### 2. Get OpenWeatherMap API Key
1. Visit [OpenWeatherMap API](https://openweathermap.org/api)
2. Create a free account
3. Generate your API key
4. Copy the API key for next step

### 3. Configure API Key
Replace `YOUR_API_KEY_HERE` with your actual API key in these files:
- `app/src/main/java/com/example/weatherapp/MainActivity.kt` (line 30)
- `app/src/main/java/com/example/weatherapp/ForecastActivity.kt` (line 19)

```kotlin
private val apiKey = "your_actual_api_key_here"
```

### 4. Build and Run
1. Open the project in Android Studio
2. Sync the project with Gradle files
3. Connect your Android device or start an emulator
4. Click "Run" or press `Ctrl+R`

## 📱 App Structure

```
app/
├── src/main/
│   ├── java/com/example/weatherapp/
│   │   ├── MainActivity.kt              # Main weather screen
│   │   ├── ForecastActivity.kt          # 5-day forecast screen
│   │   ├── WeatherAdapter.kt            # RecyclerView adapter
│   │   ├── WeatherApiService.kt         # Retrofit API interface
│   │   └── WeatherData.kt               # Data models
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml        # Main screen layout
│   │   │   ├── activity_forecast.xml    # Forecast screen layout
│   │   │   └── item_forecast.xml        # Forecast item layout
│   │   ├── drawable/                    # Background & icon resources
│   │   └── values/                      # Colors, strings, styles
│   └── AndroidManifest.xml
```

## 🎨 UI Components

### Main Screen
- **Top Bar**: City name with search functionality
- **Weather Display**: Large temperature with description
- **Temperature Range**: Daily min/max temperatures
- **AQI Indicator**: Air Quality Index display
- **Forecast Button**: Navigate to 5-day forecast

### Forecast Screen
- **Header**: Back navigation with title
- **Forecast List**: RecyclerView showing 5-day weather data
  - Day of week
  - Date
  - Weather description
  - Max/Min temperatures

## 🔧 Dependencies

```gradle
// Core Android
implementation 'androidx.core:core-ktx:1.12.0'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.11.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
implementation 'androidx.recyclerview:recyclerview:1.3.2'

// Networking
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'

// Location Services
implementation 'com.google.android.gms:play-services-location:21.0.1'

// Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0'
```

## 🔐 Permissions

The app requires the following permissions:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

## 📊 API Integration

### OpenWeatherMap API Endpoints Used:
- **Current Weather**: `https://api.openweathermap.org/data/2.5/weather`
- **5-Day Forecast**: `https://api.openweathermap.org/data/2.5/forecast`

### API Response Handling:
- Temperature conversion to Celsius
- Weather description capitalization
- Date formatting for forecast display
- Error handling for network failures

## 🚨 Error Handling

The app includes comprehensive error handling for:
- Network connectivity issues
- Location permission denied
- Invalid city names
- API rate limiting
- GPS unavailable

## 🎯 Usage

1. **First Launch**: 
   - Grant location permission when prompted
   - App automatically fetches weather for current location

2. **Search Cities**:
   - Tap the search icon (🔍) in the top right
   - Enter city name and tap "Search"

3. **View Forecast**:
   - Tap "5-day forecast" button
   - Scroll through the list to see daily forecasts
   - Tap back arrow to return to main screen

## 📈 Future Enhancements

- [ ] Hourly weather forecast
- [ ] Weather radar maps
- [ ] Weather alerts and notifications
- [ ] Multiple location management
- [ ] Dark/Light theme toggle
- [ ] Widget support
- [ ] Real AQI API integration
- [ ] Weather animations

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

If you encounter any issues or have questions:
- Create an issue on GitHub
- Check the [OpenWeatherMap API Documentation](https://openweathermap.org/api)
- Review Android location services documentation

## 🙏 Acknowledgments

- [OpenWeatherMap](https://openweathermap.org/) for providing the weather API
- Google for Android development tools and location services
- Material Design for UI components and guidelines

---

## 📸 Screenshots

[](https://github.com/PuspenduNayak/Weather_App/blob/master/Screenshot_2025-06-21-14-29-08-751_com.example.weatherapp_kotlin.jpg)
[](https://github.com/PuspenduNayak/Weather_App/blob/master/Screenshot_2025-06-21-14-29-13-611_com.example.weatherapp_kotlin.jpg)

### Main Screen
- Beautiful gradient background
- Current temperature display
- Weather description and conditions
- Min/Max temperature range
- AQI indicator

### Forecast Screen  
- Clean list design
- 5-day weather forecast
- Daily temperature ranges
- Weather descriptions

---

**Made with ❤️ for Android developers**
