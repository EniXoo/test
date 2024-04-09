package fr.flexcity.test.mapper

import fr.flexcity.test.dto.TSOResponseDTO
import fr.flexcity.test.entity.TSOResponse
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

@Service
class TSOResponseMapper(@Lazy val assetMapper: AssetMapper, @Lazy val tsoRequestMapper: TSORequestMapper) : EntityDTOMapper<TSOResponse, TSOResponseDTO>{

    override fun toEntity(dto: TSOResponseDTO): TSOResponse {
        val volume = dto.volume ?: throw IllegalArgumentException("Volume cannot be null")
        val asset = dto.asset ?: throw IllegalArgumentException("Asset cannot be null")
        val tsoRequest = dto.tsoRequest ?: throw IllegalArgumentException("TSO Request cannot be null")

        return TSOResponse(
            volume = volume,
            asset = this.assetMapper.toEntity(asset),
            tsoRequest = this.tsoRequestMapper.toEntity(tsoRequest),
            cost = dto.cost,
            id = dto.id
        )
    }

    fun toEntities(dtos: List<TSOResponseDTO>): List<TSOResponse> {
        return dtos.map { this.toEntity(it) }
    }

    override fun toDTO(entity: TSOResponse): TSOResponseDTO {
        return TSOResponseDTO(
            volume = entity.volume,
            asset = this.assetMapper.toDTO(entity.asset),
            tsoRequest = this.tsoRequestMapper.toDTO(entity.tsoRequest),
            id = entity.id,
            cost = entity.cost
        )
    }

    fun toDTOFromAsset(entity: TSOResponse): TSOResponseDTO {
        return TSOResponseDTO(
            volume = entity.volume,
            tsoRequest = this.tsoRequestMapper.toDTO(entity.tsoRequest),
            cost = entity.cost,
            id = entity.id
        )
    }

    fun toDTOsFromAsset(entities: List<TSOResponse>): List<TSOResponseDTO> {
        return entities.map { this.toDTOFromAsset(it) }
    }

    fun toDTOCreatedTSORequest(entity: TSOResponse): TSOResponseDTO {
        return TSOResponseDTO(
            volume = entity.volume,
            asset = this.assetMapper.toDTOCreatedTSORequest(entity.asset),
            id = entity.id,
            cost = entity.cost
        )
    }

    fun toDTOsCreatedTSORequest(entities: List<TSOResponse>): List<TSOResponseDTO> {
        return entities.map { this.toDTOCreatedTSORequest(it) }
    }
}