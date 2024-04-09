package fr.flexcity.test.entity

import jakarta.persistence.*

@Entity
@Table(name = "day")
data class Day (
    @Column(name = "name")
    val name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long
) {
    constructor() : this("", 0)

    @OneToMany(mappedBy = "availabilityId.day", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val availabilities: List<Availability>? = null
}