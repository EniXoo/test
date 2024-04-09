package fr.flexcity.test.mapper

interface EntityDTOMapper<E, D> {
    fun toEntity(dto: D): E
    fun toDTO(entity: E): D
}