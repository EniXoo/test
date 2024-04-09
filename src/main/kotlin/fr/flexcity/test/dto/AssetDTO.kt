package fr.flexcity.test.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AssetDTO(
    val name: String? = null,
    var activationCost: Double? = null,
    var volume: Int? = null,
    val code: String? = null,
    val availabilities: List<AvailabilityDTO>? = null,
    val tsoResponses: List<TSOResponseDTO>? = null,

    var remainingVolume: Int? = null,
)
