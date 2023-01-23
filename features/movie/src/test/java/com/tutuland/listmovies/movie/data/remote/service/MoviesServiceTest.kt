package com.tutuland.listmovies.movie.data.remote.service

import io.mockk.MockKAnnotations
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class MoviesServiceTest {
    private lateinit var server: MockWebServer
    private lateinit var service: MoviesService

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        server = MockWebServer()
        service = createMoviesService(server.url("/"))
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `when getMovies returns not found, throw HttpException`() = runTest {
        serviceResponds(404, "resource_not_found.json")
        assertFailsWith<HttpException> { service.getMovies() }
    }

    @Test
    fun `when getMovies succeed, return a BreedsResponse`() = runTest {
        serviceResponds(200, "get_movies_success.json")
        val response = service.getMovies()
        assertTrue { response.results.orEmpty().size == 20 }
    }

    private fun serviceResponds(code: Int, response: String) = server.enqueue(
        MockResponse()
            .setResponseCode(code)
            .setBody(readJson(response))
    )

    private fun readJson(
        file: String
    ) = (this::class.java.classLoader ?: throw IllegalStateException("Failed to get classLoader"))
        .getResourceAsStream(file)
        .bufferedReader()
        .use { it.readText() }
}