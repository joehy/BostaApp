package com.example.joebosta.response

import com.example.joebosta.models.Address
import com.example.joebosta.models.Company

data class UsersResponse(
    val address: Address,
    val company: Company,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)