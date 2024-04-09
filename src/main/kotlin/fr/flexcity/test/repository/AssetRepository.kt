package fr.flexcity.test.repository

import fr.flexcity.test.entity.Asset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AssetRepository: JpaRepository<Asset, String> {

    @Query("""
        select distinct a
        from Asset a
        join a.availabilities av
        where av.availabilityId.day.name = :dayName
    """)
    fun findAvailable(dayName: String): List<Asset>
}