package com.nicando.catalog.rest

import com.nicando.catalog.database.models.suppliergoods.Article
import com.nicando.catalog.database.models.suppliergoods.PriceScale
import com.nicando.catalog.database.models.suppliergoods.Supplier
import com.nicando.catalog.database.repositories.SupplierRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

/**
 * Created by Jan Adamczyk on 21.05.2019.
 */


@RestController
@RequestMapping("/List")
class SupplierController(private val supplierRepository: SupplierRepository) {

//    @GetMapping("/supplier")
//    fun getAllSuppliers(): List<Supplier> = supplierRepository.findAll()
//
//    @GetMapping("/supplier/{id}")
//    fun getSupplierById(@PathVariable(value = "id") supplierId: Long): ResponseEntity<Supplier> {
//        return supplierRepository.findById(supplierId).map { supplier ->
//            ResponseEntity.ok(supplier)
//        }.orElse(ResponseEntity.notFound().build())
//    }

    @GetMapping("/supplier")
    fun getAllSuppliers(): String = "hello"

    @GetMapping("/supplier/{id}")
    fun getSupplierById(@PathVariable(value = "id") supplierId: Long): Article {
        val priceScale = PriceScale(1, BigDecimal(10), BigDecimal(10))
        val priceList: List<PriceScale> = listOf(priceScale)
        return Article(1, "Kondensator", BigDecimal(10), "Euro", priceList)
    }
}