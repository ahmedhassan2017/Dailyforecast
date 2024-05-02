package com.example.baimsdailyforecast

import com.example.baimsdailyforecast.data.remote.ApiCityInterface
import com.example.baimsdailyforecast.models.CitiesResponse
import com.example.baimsdailyforecast.models.City1
import com.example.baimsdailyforecast.domain.repo.cities.CityRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CityRepoTest
{

    // Mocked ApiCityInterface
    @Mock lateinit var mockApiCityInterface: ApiCityInterface

    // The CityRepo instance to be tested
    lateinit var cityRepo: CityRepo

    // Initialize mocks
    @Before fun setup()
    {
        MockitoAnnotations.initMocks(this)
        cityRepo = CityRepo(mockApiCityInterface)
    }

    // Test case for getCities() method
    @Test fun `test getCities should return list of cities`() = runBlocking {
        // Mock response data
        val mockCitiesResponse = CitiesResponse(
            mutableListOf(City1(1, "CityA", "CityA", 34.0522, -118.2437), City1(2, "CityB", "CityB", 40.7128, -74.0060)))

        // Stubbing the behavior of getCitiesData() in the mocked interface
        `when`(mockApiCityInterface.getCitiesData()).thenReturn(mockCitiesResponse)

        // Call the method under test
        val citiesFlow = cityRepo.getCities()

        // Collect the emitted value from the flow within runBlocking
        citiesFlow.collect { citiesResponse ->
            // Verify that the emitted value matches the mocked response
            assertEquals(mockCitiesResponse, citiesResponse)
        }
    }

    // Test case for error scenario in getCities() method
    @Test(expected = Exception::class)
    fun `test getCities should return error`() = runBlocking {
        // Mock exception to be thrown by getCitiesData()
        val mockException = Exception("Failed to fetch cities data")

        // Stubbing the behavior of getCitiesData() to throw an exception
        `when`(mockApiCityInterface.getCitiesData()).thenThrow(mockException)

        // Call the method under test
        val citiesFlow = cityRepo.getCities()
        var isError = false
        // Collect the emitted value from the flow within runBlocking
        citiesFlow.catch {
            isError = true

        }.collect { citiesResponse ->
                // Ensure that the emitted value is null (or handle accordingly in your implementation)
                // Here we assume an error handling mechanism that returns null or a default error response
            }

        Assert.assertEquals(isError, true)

    }
}
