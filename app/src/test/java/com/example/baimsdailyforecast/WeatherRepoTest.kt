import com.example.baimsdailyforecast.data.remote.ApiInterface
import com.example.baimsdailyforecast.models.WeatherResponse
import com.example.baimsdailyforecast.domain.repo.weather.WeatherRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class WeatherRepoTest {

    // Mocked ApiInterface
    @Mock
    lateinit var mockApiInterface: ApiInterface

    // The WeatherRepo instance to be tested
    lateinit var weatherRepo: WeatherRepo

    // Initialize mocks
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherRepo = WeatherRepo(mockApiInterface)
    }

    // Test case for getWeather() method success
    @Test
    fun `test getWeather should return weather data`() = runBlocking {
        // Mock response data
        val mockWeatherResponse = WeatherResponse(
            "200",
            0,
            1,
            emptyList(),
            null
        )

        // Stubbing the behavior of getWeather() in the mocked interface
        `when`(mockApiInterface.getWeather(0.0, 0.0, "apiKey")).thenReturn(mockWeatherResponse)

        // Call the method under test
        val weatherFlow = weatherRepo.getWeather(0.0, 0.0, "apiKey")

        // Collect the emitted value from the flow within runBlocking
        weatherFlow.collect { weatherResponse ->
            // Verify that the emitted value matches the mocked response
            assertEquals(mockWeatherResponse, weatherResponse)
        }
    }

    // Test case for error scenario in getWeather() method
    @Test(expected = Exception::class)
    fun `test getWeather should return error`() = runBlocking {
        // Mock exception to be thrown by getWeather()
        val mockException = Exception("Failed to fetch weather data")

        // Stubbing the behavior of getWeather() to throw an exception
        `when`(mockApiInterface.getWeather(0.0, 0.0, "apiKey")).thenThrow(mockException)

        var errorOccurred = false

        // Call the method under test
        val weatherFlow = weatherRepo.getWeather(0.0, 0.0, "apiKey")

        // Collect the emitted value from the flow within runBlocking
        weatherFlow.catch { error ->
            // Verify that the correct error occurred
            assertTrue(error is Exception)
            assertEquals("Failed to fetch weather data", error.message)
            errorOccurred = true
        }.collect {
            // This block should not be reached in case of error
        }

        // Assert that an error was indeed caught
        assertTrue(errorOccurred)
    }
}
