package fr.flexcity.test.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AvailabilityDTO(
    val availabilityId: AvailabilityIdDTO? = null
)
