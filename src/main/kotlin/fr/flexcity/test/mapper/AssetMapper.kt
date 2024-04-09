package fr.flexcity.test.mapper

import fr.flexcity.test.dto.AssetDTO
import fr.flexcity.test.entity.Asset
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

@Service
class AssetMapper(@Lazy val availabilityMapper: AvailabilityMapper, @Lazy val tsoResponseMapper: TSOResponseMapper): EntityDTOMapper<Asset, AssetDTO>{
    override fun toEntity(dto: AssetDTO): Asset {
        var name = dto.name ?: throw IllegalArgumentException("Name cannot be null")
        var activationCost = dto.activationCost ?: throw IllegalArgumentException("Activation cost cannot be null")
        var volume = dto.volume ?: throw IllegalArgumentException("Volume cannot be null")

        return Asset(
            name = name,
            activationCost = activationCost,
            volume = volume,
            code = dto.code
        )
    }

    override fun toDTO(entity: Asset): AssetDTO {
        return AssetDTO(
            name = entity.name,
            activationCost = entity.activationCost,
            volume = entity.volume,
            code = entity.code,
            availabilities = entity.availabilities?.let { this.availabilityMapper.toDTOs(it) },
            tsoResponses = entity.tsoResponses?.let { this.tsoResponseMapper.toDTOsFromAsset(it) }
        );
    }

    fun toDTOs(entities: List<Asset>): List<AssetDTO> {
        return entities.map { this.toDTO(it) }
    }

    fun toDTOCreatedTSORequest(entity: Asset): AssetDTO {
        return AssetDTO(
            name = entity.name,
            code = entity.code
        );
    }
}