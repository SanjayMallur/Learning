package com.sanjay.learning.data

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flextrade.kfixture.KFixture
import com.sanjay.learning.data.entities.Pokemon
import com.sanjay.learning.data.repositories.PokemonRepository
import com.sanjay.learning.di.mappersModule
import com.sanjay.learning.di.networkModule
import com.sanjay.learning.di.persistenceModule
import com.sanjay.learning.fixture.setField
import com.sanjay.learning.mappers.PokemonMapper
import com.sanjay.learning.network.*
import com.sanjay.learning.paging.Response
import com.sanjay.learning.data.persistence.PokemonDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProvider
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PokemonRepositoryTest : KoinTest{
    @Suppress("unused")
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val name = "Test"

    private val pokemonClient: PokemonClient by inject()
    private val pokemonMapper: PokemonMapper by inject()
    private val pokemonDao: PokemonDao by inject()
    private val fixture = KFixture()
    private val context = mock<Application>()
    private lateinit var pokemonRepository: PokemonRepository

    val  pokemon = Pokemon(name = "bulbasaur", url = "url")

    @Before
    fun setUp() {
        fixture.jFixture.customise().circularDependencyBehaviour().omitSpecimen()
        setUpKoin()
        initMocks()
        pokemonRepository = PokemonRepository(
            pokemonClient,
            pokemonMapper,
            pokemonDao,
        )
    }

    @Test
    fun `Given local list copy does not exist When view model is initialized Then list is updated from network`() =
        runBlocking {
            //Given
            val mockedPokemonResponseDto = getPokemonResponseDtoFixture()

            //When
            whenever(pokemonMapper.asEntity(mockedPokemonResponseDto)).thenReturn(getRemoteResponse())
            whenever(pokemonClient.getPokemonList(0)).thenReturn(mockedPokemonResponseDto)
            whenever(pokemonDao.getPokemonList(0)).thenReturn(listOf())
            whenever(pokemonDao.getAllPokemonList()).thenReturn(listOf(pokemon))
            val response = pokemonRepository.getPokemonList(0)

            //Then
            Assert.assertEquals(getRemoteResponse(), response)
        }

    @Test
    fun `Given local list copy does exist When view model is initialized Then list is updated from room`() =
        runBlocking {
            //When
            whenever(pokemonDao.getPokemonList(0)).thenReturn(listOf(pokemon))
            whenever(pokemonDao.getAllPokemonList()).thenReturn(listOf(pokemon))
            val response = pokemonRepository.getPokemonList(0)

            //Then
            assertEquals(getLocalResponse(), response)
        }

    private fun getPokemonResponseDtoFixture(): PokemonResponseDto {
        val pokemonDto = fixture<PokemonDto>().apply {
            setField("name", "bulbasaur")
            setField("url", "url")
        }
        return fixture<PokemonResponseDto>().apply {
            setField("count", 20)
            setField("next", name)
            setField("previous", name)
            setField("results", listOf(pokemonDto))
        }
    }

    private fun initMocks(){
        MockProvider.register { Mockito.mock(it.java) }
        declareMock<PokemonClient>()
        declareMock<PokemonDao>()
        declareMock<PokemonMapper>()
    }

    private fun setUpKoin(){
        startKoin {
            androidContext(context)
            modules(networkModule + persistenceModule + mappersModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun getRemoteResponse() = Response(
        count = 20,
        next = name,
        previous = name,
        result = listOf(pokemon)
    )

    private fun getLocalResponse() = Response(
        count = 0,
        next = null,
        previous = null,
        result = listOf(pokemon)
    )

}
