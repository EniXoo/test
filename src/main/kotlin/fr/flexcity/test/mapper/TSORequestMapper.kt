package fr.flexcity.test.mapper

import fr.flexcity.test.dto.TSORequestDTO
import fr.flexcity.test.entity.TSORequest
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

@Service
class TSORequestMapper(@Lazy val tsoResponseMapper: TSOResponseMapper): EntityDTOMapper<TSORequest, TSORequestDTO>{
    override fun toEntity(dto: TSORequestDTO): TSORequest {
        val activationDate = dto.activationDate ?: throw IllegalArgumentException("Activation date cannot be null")
        val volume = dto.volume ?: throw IllegalArgumentException("Volume cannot be null")

        return TSORequest(
            id = dto.id,
            activationDate = activationDate,
            volume = volume,
            requestedBy = "Extracted from Auth",
            cost = dto.cost,
        )
    }

    override fun toDTO(entity: TSORequest): TSORequestDTO {
        return TSORequestDTO(
            activationDate = entity.activationDate,
            volume = entity.volume,
            requestedAt = entity.requestedAt,
            requestedBy = entity.requestedBy,
            id = entity.id,
            cost = entity.cost,
        )
    }

    fun toDTOCreatedTSORequest(entity: TSORequest): TSORequestDTO {
        return TSORequestDTO(
            volume = entity.volume,
            tsoResponses = this.tsoResponseMapper.toDTOsCreatedTSORequest(entity.tsoResponses!!),
            cost = entity.cost,
        )
    }
}