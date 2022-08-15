package com.example.mongocrud.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

data class Employee(
    @Id
    val id: ObjectId? = null,
    var firstName: String,
    var lastName: String,
    var email: String,
    var company: Company?
)
