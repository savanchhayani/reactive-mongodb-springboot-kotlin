package com.example.mongocrud.controller

import com.example.mongocrud.request.CompanyRequest
import com.example.mongocrud.response.CompanyResponse
import com.example.mongocrud.service.CompanyService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@RestController
@RequestMapping("/api/company")
class CompanyController(
    private val companyService: CompanyService
) {
    @PostMapping
    fun createCompany(@RequestBody request: CompanyRequest): Mono<CompanyResponse> =
        companyService.createCompany(request)
            .map { CompanyResponse.fromEntity(it) }

    @GetMapping
    fun findAllCompanies(): Flux<CompanyResponse> =
        companyService.findAll()
            .map { CompanyResponse.fromEntity(it) }
            .delayElements(Duration.ofSeconds(2))

    @GetMapping("/{id}")
    fun findCompanyById(@PathVariable id: String): Mono<CompanyResponse> =
        companyService.findById(id)
            .map { CompanyResponse.fromEntity(it) }

    @PutMapping("/{id}")
    fun updateCompany(
        @PathVariable id: String,
        @RequestBody request: CompanyRequest
    ): Mono<CompanyResponse> =
        companyService.updateCompany(id, request)
            .map { CompanyResponse.fromEntity(it) }

    @DeleteMapping("/{id}")
    fun deleteCompany(@PathVariable id: String): Mono<Void> =
        companyService.deleteById(id)
}
