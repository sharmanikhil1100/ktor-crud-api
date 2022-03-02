package com.example

import com.example.dao.DatabaseFactory
import com.example.dao.EmployeeDao
import com.example.dao.models.Employee
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database

//val dao = EmployeeDao(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(CallLogging)
        install(ContentNegotiation){
            jackson {}
        }
        DatabaseFactory.init()
        val dao = EmployeeDao(DatabaseFactory.db?:
                                Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))
        dao.init()
        routing{
            route("/employee") {
                get("/") {
                    val empAll = dao.getAllEmployees()
                    if (empAll != null)
                        call.respond(empAll.toString())
                    else
                        call.respond("Empty")
                }
                post("/") {
                    val emp = call.receive<Employee>()
                    dao.createEmployee(emp.name, emp.email, emp.city)
                }
                put("/") {
                    val emp = call.receive<Employee>()
                    dao.updateEmployee(emp.id, emp.name, emp.email, emp.city)
                }
                delete("/{id}") {
                    val id = call.parameters["id"]
                    if (id != null) {
                        dao.deleteEmployee(id.toInt())
                    } else {
                        call.respond("Didn't receive id to delete. Please pass id")
                    }
                }
            }
        }
    }.start(wait = true)
}
