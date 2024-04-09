package fr.flexcity.test.entity

import jakarta.persistence.*

@Entity
@Table(name = "availability")
data class Availability(
    @EmbeddedId
    val availabilityId: AvailabilityId? = null,
)