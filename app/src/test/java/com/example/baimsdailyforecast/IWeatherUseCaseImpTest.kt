package com.example.baimsdailyforecast
import com.example.baimsdailyforecast.models.City
import com.example.baimsdailyforecast.models.Clouds
import com.example.baimsdailyforecast.models.Coord
import com.example.baimsdailyforecast.models.MainWeatherInfo
import com.example.baimsdailyforecast.models.WeatherData
import com.example.baimsdailyforecast.models.WeatherDescription
import com.example.baimsdailyforecast.models.WeatherResponse
import com.example.baimsdailyforecast.models.Wind
import com.example.baimsdailyforecast.domain.repo.weather.IWeatherRepo
import com.example.baimsdailyforecast.domain.usecases.getweather.IWeatherUseCase
import com.example.baimsdailyforecast.domain.usecases.getweather.IWeatherUseCaseImp
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class IWeatherUseCaseImpTest {

    @Mock
    private lateinit var mockRepo: IWeatherRepo

    private lateinit var weatherUseCase: IWeatherUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        weatherUseCase = IWeatherUseCaseImp(mockRepo)
    }

    @Test
    fun `testGetWeather_success`() = runBlocking{
        // Mock expected WeatherResponse
        val expectedResponse = createMockWeatherResponse()

        // Mock repository behavior (use Flowable for RxJava 3)
        val flowable = flowOf(expectedResponse)
        `when`(mockRepo.getWeather(anyDouble(), anyDouble(), anyString())).thenReturn(flowable)

        // Call the method under test
        val actualWeatherFlowable = weatherUseCase.getWeather(10.0, 20.0, "fakeApiKey")

        // Verify return type and emitted value
//        assert(actualWeatherFlowable is Flowable<*>)

        // Verify emitted value using a TestObserver (consider RxJava 3 features)
        val testObserver = actualWeatherFlowable.collect{
            assert(it== expectedResponse)
        }


    }

    @Test(expected = Exception::class)
    fun `testGetWeather_error`()= runBlocking {
        // Mock repository behavior to throw an exception
        val expectedError = RuntimeException("Network error")
        `when`(mockRepo.getWeather(anyDouble(), anyDouble(), anyString())).thenThrow(expectedError)

        // Call the method under test
        val actualWeatherFlowable = weatherUseCase.getWeather(10.0, 20.0, "fakeApiKey")



        var isError = false
        // Verify emitted error
        val testObserver = actualWeatherFlowable.catch {
                isError = true
            }
            .collect {
                // This block should not be reached as an exception is expected
                isError = false
            }

        Assert.assertEquals(isError, true)

    }

    private fun createMockWeatherResponse(): WeatherResponse {
        return   WeatherResponse(
            cod = "200", // Success code (replace with actual value)
            message = null,
            cnt = 40, // Number of data points (replace with actual value)
            list = listOf(
                WeatherData(
                    dt = 1651473600L, // Timestamp (replace with actual value)
                    main = MainWeatherInfo(
                        temp = 28.5, // Temperature (replace with actual value)
                        humidity = 60 // Humidity (replace with actual value)
                    ),
                    weather = listOf(
                        WeatherDescription(
                            id = 800, // Weather ID (replace with actual value)
                            main = "Clear", // Main weather description (replace with actual value)
                            description = "clear sky" // Detailed weather description (replace with actual value)
                        )
                    ),
                    clouds = Clouds(all = 10), // Cloud cover (replace with actual value)
                    wind = Wind(speed = 5.0), // Wind speed (replace with actual value)
                    dt_txt = "2024-05-02 00:00:00" // Date and time string (replace with actual value)
                )
                // Add more WeatherData objects as needed
            ),
            city = City(
                id = 1275339, // City ID (replace with actual value)
                name = "Cairo", // City name (replace with actual value)
                coord = Coord(lat = 30.0650, lon = 31.2497), // City coordinates (replace with actual values)
                country = "EG" // Country code (replace with actual value)
            )
        )



    }
}
