package learning

import spark.kotlin.*
import java.io.File
import java.util.logging.Logger;

fun main(args: Array<String>) {
    val http: Http = ignite()
    println("Hello!")

    fun serverError(code: Int = 500, message: String = "Internal server error"): String {
        return """
            <h1 style="font-family:monospace">$message (Error $code)</h1>
            <p style="font-family:monospace">We're sorry, but our server messed up. Please back and try again.</p>
        """
    }

    http.get("/") {
        try {
            val file: String = File("src/main/kotlin/learning/index.html").readText()
            file
        } catch (e: Exception) {
            println(e.message)
        } finally {
            serverError()
        }
    }

    http.get("/hello/:name") {
        val name: String = params("name")
        var randoNum: Int = (0..10).random()
        "Hello " + name + " " + randoNum
    }

    http.internalServerError() {
        
        serverError()
    }
}