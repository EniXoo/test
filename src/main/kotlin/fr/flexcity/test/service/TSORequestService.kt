package fr.flexcity.test.service

import fr.flexcity.test.dto.AssetDTO
import fr.flexcity.test.dto.TSORequestDTO
import fr.flexcity.test.dto.TSOResponseDTO
import fr.flexcity.test.entity.TSORequest
import fr.flexcity.test.mapper.AssetMapper
import fr.flexcity.test.mapper.TSORequestMapper
import fr.flexcity.test.mapper.TSOResponseMapper
import fr.flexcity.test.repository.AssetRepository
import fr.flexcity.test.repository.TSORequestRepository
import fr.flexcity.test.repository.TSOResponseRepository
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class TSORequestService() {
    @Autowired
    lateinit var tsoRequestRepository: TSORequestRepository

    @Autowired
    lateinit var tsoRequestMapper: TSORequestMapper

    @Autowired
    lateinit var assetRepository: AssetRepository

    @Autowired
    lateinit var assetMapper: AssetMapper

    @Autowired
    lateinit var tsoResponseRepository: TSOResponseRepository

    @Autowired
    lateinit var tsoResponseMapper: TSOResponseMapper

    val simpleDateFormatDDMMYYYY = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
    val simpleDateFormatEEEE = SimpleDateFormat("EEEE", Locale.ENGLISH)

    /**
     * Create a TSORequest
     * @param tsoRequestDTO the TSO DTO
     * @return the solution
     */
    fun createTSORequest(tsoRequestDTO: TSORequestDTO): TSORequestDTO {
        var tsoRequest = this.tsoRequestRepository.save(this.tsoRequestMapper.toEntity(tsoRequestDTO))

        val assets = this.getAvailableAssets(tsoRequestDTO.activationDate!!)
        val solution = this.getBestSolution(tsoRequest, assetDTOs = assets)

        tsoRequest.cost = solution.cost
        tsoRequest.tsoResponses = this.tsoResponseMapper.toEntities(solution.tsoResponses!!)
        tsoRequest = this.tsoRequestRepository.save(tsoRequest)

        return this.tsoRequestMapper.toDTOCreatedTSORequest(tsoRequest)
    }

    /**
     * Get available assets for a given date
     * @param date the date
     * @return the list of available assets
     *
     * Note : Assets volume is updated with the volume already used for the given date
     */
    fun getAvailableAssets(date: Date): List<AssetDTO> {
        val dayName = simpleDateFormatEEEE.format(date).uppercase()
        val assetDTOs = this.assetMapper.toDTOs(assetRepository.findAvailable(dayName))
        var tsoResponsesSameDate: List<TSOResponseDTO>

        return assetDTOs.map { asset ->
            asset.apply {
                tsoResponsesSameDate = tsoResponses!!.filter { tsoResponse ->
                    simpleDateFormatDDMMYYYY.format(tsoResponse.tsoRequest!!.activationDate) == simpleDateFormatDDMMYYYY.format(date)
                }

                remainingVolume = volume!! - tsoResponsesSameDate.sumOf { it.volume!! }
            }
        }.filter { it.remainingVolume!! > 0 }.sortedWith(compareBy<AssetDTO> { it.activationCost!! / it.volume!! }.thenByDescending { it.remainingVolume })
    }

    /**
     * Get the best solution for a given power and a list of assets
     * @param tsoRequestDTO the TSORequest DTO
     * @param requiredVolume the queried power
     * @param assetDTOs the list of assets
     * @param tsoResponseDTOs the list of TSOResponse DTO
     *
     * Note : The list of assets has to be sorted by cost per volume & volume
     */
    fun getBestSolution(tsoRequest: TSORequest, requiredVolume: Int = tsoRequest.volume, assetDTOs: List<AssetDTO>, tsoResponseDTOs: LinkedList<TSOResponseDTO> = LinkedList()): TSORequestDTO {
        return if(requiredVolume == 0) {
            TSORequestDTO(
                cost = tsoResponseDTOs.sumOf { it.cost!! },
                volume = tsoResponseDTOs.sumOf { it.volume!! },
                tsoResponses = tsoResponseDTOs
            )
        } else if(assetDTOs.isNotEmpty()) {
            val assetDTO = assetDTOs.first()
            val newRemainingVolume = assetDTO.remainingVolume!! - requiredVolume
            val tsoResponseVolume = if(newRemainingVolume > 0) requiredVolume else assetDTO.remainingVolume!!
            val newRequiredVolume = if(newRemainingVolume > 0) 0 else requiredVolume - assetDTO.remainingVolume!!
            val tsoResponseCost = String.format("%.2f", assetDTO.activationCost!! * (tsoResponseVolume / assetDTO.volume!!.toDouble())).replace(',', '.').toDouble()

            tsoResponseDTOs.add(TSOResponseDTO(volume = tsoResponseVolume, asset = assetDTO, tsoRequest = this.tsoRequestMapper.toDTO(tsoRequest), cost = tsoResponseCost.toDouble()))
            getBestSolution(tsoRequest, newRequiredVolume, assetDTOs.drop(1), tsoResponseDTOs)
        } else {
            throw BadRequestException("Pas de solution possible")
        }
    }
}