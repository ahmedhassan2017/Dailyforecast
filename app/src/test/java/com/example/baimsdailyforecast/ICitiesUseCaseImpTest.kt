package com.example.baimsdailyforecast
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.baimsdailyforecast.models.CitiesResponse
import com.example.baimsdailyforecast.models.City1
import com.example.baimsdailyforecast.domain.repo.cities.ICityRepo
import com.example.baimsdailyforecast.domain.usecases.getcities.ICitiesUseCaseImp
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ICitiesUseCaseImpTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Ensures LiveData updates on the main thread

    @Mock
    private lateinit var mockRepo: ICityRepo

    private lateinit var citiesUseCase: ICitiesUseCaseImp

    @Before
    fun setUp() {
        // Initialize mockRepo using a mocking library like MockK
        MockitoAnnotations.initMocks(this)

        citiesUseCase = ICitiesUseCaseImp(mockRepo)
    }


    @Test
    fun `getCities returns flow of CitiesResponse from repository`() = runBlocking {
        val expectedCities = listOf(
            City1(1, "مدينة نموذجية", "Sample City", 12.34, 56.78)
        )
        val expectedFlow = flowOf(CitiesResponse(expectedCities.toMutableList()))

        Mockito.`when`(mockRepo.getCities()).thenReturn(expectedFlow)

        val actualFlow = citiesUseCase.getCities()
        val actualCities = mutableListOf<City1>()
        actualFlow.collect { actualCities.addAll(it.cities) }

        // Assert that the collected cities match the expected list
        assert(actualCities == expectedCities)
    }


    @Test(expected = Exception::class)
    fun `testGetCities_error`()= runBlocking {
        // Mock repository behavior to throw an exception
        val expectedError = RuntimeException("Network error")
        Mockito.`when`(mockRepo.getCities()).thenThrow(expectedError)

        // Call the method under test
        val actualWeatherFlowable = citiesUseCase.getCities()



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

}
