package com.sanjay.learning.data

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flextrade.kfixture.KFixture
import com.sanjay.learning.di.mappersModule
import com.sanjay.learning.di.networkModule
import com.sanjay.learning.di.persistenceModule
import com.sanjay.learning.fixture.setField
import com.sanjay.learning.mappers.PokemonMapper
import com.sanjay.learning.network.*
import com.sanjay.learning.persistence.PokemonDetailsDao
import kotlinx.coroutines.*
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

class PokemonDetailsRepositoryTest : KoinTest {
    @Suppress("unused")
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val name = "Test"

    private val pokemonClient: PokemonClient by inject()
    private val pokemonMapper: PokemonMapper by inject()
    private val pokemonDetailsDao: PokemonDetailsDao by inject()
    private val fixture = KFixture()
    private val context = mock<Application>()
    private lateinit var pokemonDetailsRepository: PokemonDetailsRepository

    @Before
    fun setUp() {
        fixture.jFixture.customise().circularDependencyBehaviour().omitSpecimen()
        setUpKoin()
        declareMocks()
        pokemonDetailsRepository = PokemonDetailsRepository(
            pokemonClient,
            pokemonMapper,
            pokemonDetailsDao,
        )
    }

    @Test
    fun `Given item does not exist in room When view model is initialized then details is updated from network`() =
        runBlocking {

            //Given
            val mockedPokemonDetailsDto = getPokemonDetailsDtoFixture()
            //When
            whenever(pokemonMapper.asEntity(mockedPokemonDetailsDto)).thenReturn(getPokemonDetails())
            whenever(pokemonDetailsDao.getPokemonDetails(name)).thenReturn(null)
            whenever(pokemonClient.getPokemonDetails(name)).thenReturn(mockedPokemonDetailsDto)
            val details = pokemonDetailsRepository.getPokemonDetails(name)

            //Then
            Assert.assertEquals(details, getPokemonDetails())
        }


    @Test
    fun `Given item does exist in room When view model is initialized then details is updated from room`() =
        runBlocking {
            //When
            whenever(pokemonDetailsDao.getPokemonDetails(name)).thenReturn(getPokemonDetails())
            val details = pokemonDetailsRepository.getPokemonDetails(name)

            //Then
            Assert.assertEquals(details, getPokemonDetails())
        }

    @After
    fun tearDown() {
        stopKoin()
    }

    private fun getPokemonDetailsDtoFixture(): PokemonDetailsDto {
        return fixture<PokemonDetailsDto>().apply {
            setField("id", 1)
            setField("name", name)
            setField("height", 100)
            setField("weight", 200)
            setField("experience", 12)
            setField("types", emptyList<TypeResponseDto>())
        }
    }

    private fun getPokemonDetails(): PokemonDetails {
        return PokemonDetails(
            id = 1,
            name = name,
            height = 100,
            weight = 200,
            experience = 12,
            types = listOf()
        )
    }

    private fun declareMocks() {
        MockProvider.register { Mockito.mock(it.java) }
        declareMock<PokemonClient>()
        declareMock<PokemonDetailsDao>()
        declareMock<PokemonMapper>()
    }

    private fun setUpKoin() {
        startKoin {
            androidContext(context)
            modules(networkModule + persistenceModule + mappersModule)
        }
    }
}
