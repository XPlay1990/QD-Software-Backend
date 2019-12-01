package com.nicando.ediportal.database.model.edi.questions

/**
 * @author : j_ada
 * @since : 01.12.2019, So.
 **/
enum class TransferStandards(name: String) {
    SAP_IDOC("SAP IDOC"),
    EDIFACT("EDIFACT"),
    VDA("VDA"),
    BEMIS("BEMIS")
}