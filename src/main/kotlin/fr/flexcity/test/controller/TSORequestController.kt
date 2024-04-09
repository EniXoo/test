package fr.flexcity.test.controller

import fr.flexcity.test.dto.TSORequestDTO
import fr.flexcity.test.service.TSORequestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1/tso/requests")
class TSORequestController {
    @Autowired
    lateinit var tsoRequestService: TSORequestService

    @PostMapping
    fun createTSORequest(@Valid @RequestBody tsoRequest: TSORequestDTO): ResponseEntity<TSORequestDTO> {
        return ResponseEntity.ok().body(this.tsoRequestService.createTSORequest(tsoRequest));
    }
}