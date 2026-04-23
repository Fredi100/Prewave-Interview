package com.example.demo

import com.example.demo.dto.EdgeDto
import com.example.demo.dto.ErrorDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/edge")
class EdgeController {

    final val edgeRepository: EdgeRepository

    @Autowired
    constructor(edgeRepository: EdgeRepository) {
        this.edgeRepository = edgeRepository
    }

    // TODO: Have to do some more validation and also better Exception handling
    @PostMapping("/create")
    fun createEdge(@RequestBody edge: EdgeDto): ResponseEntity<Any> {
        try {
            edgeRepository.createEdge(edge)
            return ResponseEntity.status(HttpStatus.CREATED).body(edge)
        } catch (_: DuplicateKeyException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorDto("Edge from ${edge.fromId} to ${edge.toId} already exists"))
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto(ex.message ?: "An unexpected error occurred"))
        }
    }

    @PostMapping("/delete")
    fun deleteEdge(@RequestBody edge: EdgeDto): ResponseEntity<Any> {
        try {
            val result = edgeRepository.deleteEdge(edge)

            if (result > 0)
                return ResponseEntity.noContent().build()

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDto("Edge from ${edge.fromId} to ${edge.toId} does not exist"))
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto(ex.message ?: "An unexpected error occurred"))
        }
    }
}