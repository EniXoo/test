package fr.flexcity.test.mapper

import fr.flexcity.test.dto.AvailabilityIdDTO
import fr.flexcity.test.entity.AvailabilityId
import org.springframework.stereotype.Service

@Service
class AvailabilityIdMapper: EntityDTOMapper<AvailabilityId, AvailabilityIdDTO> {
    override fun toEntity(dto: AvailabilityIdDTO): AvailabilityId {
        TODO("Not yet implemented")
    }

    override fun toDTO(entity: AvailabilityId): AvailabilityIdDTO {
        return AvailabilityIdDTO(
            assetCode = entity.asset.code,
            dayId = entity.day.id
        )
    }

}