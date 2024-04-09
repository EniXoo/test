package fr.flexcity.test.mapper

import fr.flexcity.test.dto.AvailabilityDTO
import fr.flexcity.test.entity.Availability
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

@Service
class AvailabilityMapper(@Lazy val availabilityIdMapper: AvailabilityIdMapper): EntityDTOMapper<Availability, AvailabilityDTO>{

    override fun toEntity(dto: AvailabilityDTO): Availability {
        TODO("Not yet implemented")
    }

    override fun toDTO(entity: Availability): AvailabilityDTO {
        return AvailabilityDTO(
            availabilityId = this.availabilityIdMapper.toDTO(entity.availabilityId!!)
        )
    }

    fun toDTOs(entities: List<Availability>): List<AvailabilityDTO> {
        return entities.map { this.toDTO(it) }
    }
}