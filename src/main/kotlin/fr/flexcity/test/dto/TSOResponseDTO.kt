package fr.flexcity.test.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TSOResponseDTO(
    val volume: Int? = null,
    val asset: AssetDTO? = null,
    val tsoRequest: TSORequestDTO? = null,
    val id: Long? = null,
    val cost: Double? = null
)
