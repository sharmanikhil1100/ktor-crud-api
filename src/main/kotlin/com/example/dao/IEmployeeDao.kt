package com.example.dao

import com.example.dao.models.Employee
import java.io.Closeable

interface IEmployeeDao: Closeable {
    fun init()
    fun createEmployee(name:String, email:String, city:String)
    fun updateEmployee(id:Int, name:String, email: String, city: String)
    fun deleteEmployee(id:Int)
    fun getEmployee(id:Int): Employee?
    fun getAllEmployees(): List<Employee>
}