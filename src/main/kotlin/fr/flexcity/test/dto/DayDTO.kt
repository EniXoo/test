package fr.flexcity.test.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DayDTO(
    val id: Int? = null,
    val name: String? = null,
    val availabilities: List<AvailabilityDTO>? = null
)
