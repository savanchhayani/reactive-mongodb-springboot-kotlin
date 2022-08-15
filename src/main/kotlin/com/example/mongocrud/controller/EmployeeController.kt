package com.example.mongocrud.controller

import com.example.mongocrud.request.EmployeeRequest
import com.example.mongocrud.response.EmployeeResponse
import com.example.mongocrud.service.EmployeeService
import org.bson.types.ObjectId
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

@RestController
@RequestMapping("/api/employee")
class EmployeeController(
    private val employeeService: EmployeeService
) {
    @PostMapping
    fun createEmployee(@RequestBody request: EmployeeRequest): Mono<EmployeeResponse> =
        employeeService.createEmployee(request)
            .map { EmployeeResponse.fromEntity(it) }

    @GetMapping
    fun findAllEmployees(): Flux<EmployeeResponse> =
        employeeService.findAll()
            .map { EmployeeResponse.fromEntity(it) }

    @GetMapping("/{id}")
    fun findEmployeeById(@PathVariable id: ObjectId): Mono<EmployeeResponse> =
        employeeService.findById(id)
            .map { EmployeeResponse.fromEntity(it) }

    @GetMapping("/company/{companyId}")
    fun findAllByCompanyId(@PathVariable companyId: String): Flux<EmployeeResponse> =
        employeeService.findAllByCompanyId(companyId)
            .map { EmployeeResponse.fromEntity(it) }

    @PutMapping("/{id}")
    fun updateEmployee(
        @PathVariable id: ObjectId,
        @RequestBody request: EmployeeRequest
    ): Mono<EmployeeResponse> =
        employeeService.updateEmployee(id, request)
            .map { EmployeeResponse.fromEntity(it) }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: ObjectId): Mono<Void> =
        employeeService.deleteById(id)
}
