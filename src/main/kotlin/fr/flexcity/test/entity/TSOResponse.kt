package fr.flexcity.test.entity

import jakarta.persistence.*

@Entity
@Table(name = "tso_response")
data class TSOResponse(
    @Column(name = "volume")
    val volume: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_code", referencedColumnName = "code")
    val asset: Asset,
    @ManyToOne
    @JoinColumn(name = "tso_id", referencedColumnName = "id")
    val tsoRequest: TSORequest,
    @Column(name = "cost")
    val cost: Double? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    constructor() : this(0, Asset(), TSORequest())
}