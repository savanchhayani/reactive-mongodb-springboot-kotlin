package com.example.mongocrud.response

import com.example.mongocrud.models.Employee

class EmployeeResponse(
    id: String,
    firstName: String,
    lastName: String,
    email: String,
    company: CompanyResponse?
) {
    companion object {
        fun fromEntity(employee: Employee): EmployeeResponse =
            EmployeeResponse(
                id = employee.id!!.toHexString(),
                firstName = employee.firstName,
                lastName = employee.lastName,
                email = employee.email,
                company = employee.company?.let { CompanyResponse.fromEntity(it) }
            )
    }
}
