package fr.flexcity.test.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "tso_request")
data class TSORequest(
    @Column(name = "activation_date")
    val activationDate: Date,
    @Column(name = "volume")
    val volume: Int,
    @Column(name = "requested_at")
    val requestedAt: Date = Date(),
    @Column(name = "requested_by")
    val requestedBy: String,
    @Column(name = "cost")
    var cost: Double? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
    constructor() : this(Date(), 0, Date(), "")

    @OneToMany(mappedBy = "tsoRequest", cascade = [CascadeType.ALL])
    var tsoResponses: List<TSOResponse>? = null
}