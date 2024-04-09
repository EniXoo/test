package fr.flexcity.test.entity

import jakarta.persistence.*

@Entity
@Table(name = "asset")
data class Asset(
    @Column(name = "name")
    var name: String,
    @Column(name = "activation_cost")
    var activationCost: Double,
    @Column(name = "volume")
    var volume: Int,
    @Id
    @Column(name = "code")
    var code: String? = null
) {
    constructor() : this("", 0.0, 0)

    @OneToMany(mappedBy = "availabilityId.asset", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var availabilities: List<Availability>? = null
    @OneToMany(mappedBy = "asset", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var tsoResponses: List<TSOResponse>? = null
}
