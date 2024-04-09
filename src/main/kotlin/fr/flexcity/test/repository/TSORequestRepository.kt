package fr.flexcity.test.repository

import fr.flexcity.test.entity.TSORequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TSORequestRepository : JpaRepository<TSORequest, Long> {
}