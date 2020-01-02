package com.qd.portal.excel

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author : j_ada
 * @since : 02.01.2020, Do.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class ExcelServiceTest {


    @Autowired
    lateinit var excelService: ExcelService

    @Test
    fun testCreateEdiConnectionsExcelRepresentation() {
        excelService.createEdiConnectionsExcelRepresentation(true)
    }

//    @Deployment
//    fun createDeployment(): JavaArchive {
//        return ShrinkWrap.create(JavaArchive::class.java)
//                .addClass(ExcelService::class.java)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
//    }

}
