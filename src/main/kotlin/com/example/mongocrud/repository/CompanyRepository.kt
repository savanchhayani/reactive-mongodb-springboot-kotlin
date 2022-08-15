package com.example.mongocrud.repository

import com.example.mongocrud.models.Company
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CompanyRepository : ReactiveMongoRepository<Company, String>
