package fr.flexcity.test.repository

import fr.flexcity.test.entity.TSOResponse
import org.springframework.data.jpa.repository.JpaRepository

interface TSOResponseRepository: JpaRepository<TSOResponse, Long> {
}