package com.example.mongocrud.service

import com.example.mongocrud.exception.NotFoundException
import com.example.mongocrud.models.Company
import com.example.mongocrud.models.Employee
import com.example.mongocrud.repository.CompanyRepository
import com.example.mongocrud.repository.EmployeeRepository
import com.example.mongocrud.request.CompanyRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val employeeRepository: EmployeeRepository
) {
    fun createCompany(request: CompanyRequest): Mono<Company> =
        companyRepository.save(
            Company(
                name = request.name,
                address = request.address
            )
        )

    fun findAll(): Flux<Company> = companyRepository.findAll()

    fun findById(id: String): Mono<Company> =
        companyRepository.findById(id)
            .switchIfEmpty(
                Mono.error(
                    NotFoundException("Company with id $id not found")
                )
            )

    fun updateCompany(id: String, request: CompanyRequest): Mono<Company> =
        findById(id)
            .flatMap {
                companyRepository.save(
                    it.apply {
                        name = request.name
                        address = request.address
                    }
                )
            }
            .doOnSuccess { updateCompanyEmployees(it).subscribe() }

    fun deleteById(id: String): Mono<Void> =
        findById(id)
            .flatMap(companyRepository::delete)

    private fun updateCompanyEmployees(updatedCompany: Company): Flux<Employee> =
        employeeRepository.saveAll(
            employeeRepository.findByCompanyId(updatedCompany.id!!)
                .map { it.apply { company = updatedCompany } }
        )
}
