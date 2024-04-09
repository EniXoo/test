package fr.flexcity.test.service

import fr.flexcity.test.entity.*
import fr.flexcity.test.mapper.AssetMapper
import fr.flexcity.test.mapper.AvailabilityIdMapper
import fr.flexcity.test.mapper.AvailabilityMapper
import fr.flexcity.test.mapper.TSOResponseMapper
import fr.flexcity.test.repository.AssetRepository
import fr.flexcity.test.repository.TSORequestRepository
import io.mockk.every
import io.mockk.mockk
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

class TSORequestServiceTest {
    private lateinit var entityManager: EntityManager
    private lateinit var mockTSORequestRepository: TSORequestRepository
    private lateinit var mockAssetRepository: AssetRepository
    private lateinit var mockAssetMapper: AssetMapper
    private lateinit var tsoRequestService: TSORequestService

    private var assets: List<Asset> = emptyList()

    private val date = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    }.time

    @BeforeEach
    fun setUp() {
        entityManager = mockk(relaxed = true)
        mockTSORequestRepository = mockk(relaxed = true)
        mockAssetRepository = mockk(relaxed = true)
        mockAssetMapper = mockk(relaxed = true)
        tsoRequestService = TSORequestService().apply {
            tsoRequestRepository = mockTSORequestRepository
            assetRepository = mockAssetRepository
            assetMapper = mockAssetMapper
        }
        val asset1 = Asset(name = "A1", activationCost = 100.0, volume = 60, code = "A1")
        val asset2 = Asset(name = "A2", activationCost = 110.0, volume = 60, code = "A2")

        val day1 = Day(name = "MONDAY", id=1L)

        val availabilityId1 = AvailabilityId(asset = asset1, day = day1)
        val availabilityId2 = AvailabilityId(asset = asset2, day = day1)

        val tsoRequest1 = TSORequest(activationDate = date, volume = 60, requestedBy = "user1", requestedAt = date, cost = 100.0, id = 1L)
        val tsoResponse1 = TSOResponse(volume = 60, asset = asset2, tsoRequest = tsoRequest1, cost = 100.0, id = 1L)

        asset1.availabilities = listOf(Availability(availabilityId1))
        asset2.availabilities = listOf(Availability(availabilityId2))

        asset1.tsoResponses = listOf(tsoResponse1)
        asset2.tsoResponses = emptyList()

        entityManager.persist(asset1)
        entityManager.persist(asset2)

        entityManager.flush()

        assets = listOf(
            asset1,
            asset2
        )
    }

    /**
     * This test is currently failing because availabilities & tsoResponses are not being persisted in Asset entities.
     * Didn't solve it yet.
     */
    @Test
    fun `getAvailableAssets should return a list of AssetDTO`() {
        // Given
        every { mockAssetRepository.findAvailable("MONDAY") } returns assets
        every { tsoRequestService.assetMapper.toDTOs(any()) } answers { callOriginal() }
        every { mockAssetMapper.toDTO(any()) } answers { callOriginal() }

        // When
        val result = tsoRequestService.getAvailableAssets(date)

        // Then
        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(listOf("A2"), result.map { it.code })
    }
}