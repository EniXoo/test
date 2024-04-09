package fr.flexcity.test.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.Date
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Min

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TSORequestDTO(
    @get:FutureOrPresent(message = "Activation date must be in the future or present")
    val activationDate: Date? = null,
    @get:Min(value = 1, message = "Volume must be greater than 0")
    val volume: Int? = null,
    val requestedAt: Date? = null,
    val requestedBy: String? = null,
    val id: Long? = null,
    val tsoResponses: List<TSOResponseDTO>? = null,
    val cost: Double? = null
)
