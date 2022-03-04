package com.example.dao.models

import org.jetbrains.exposed.sql.Table

object Employees: Table(){
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 50)
    val email = varchar("email", 100)
    val city = varchar("city", 50)

}

data class Employee(val id: Int, val name: String, val email: String, val city: String)