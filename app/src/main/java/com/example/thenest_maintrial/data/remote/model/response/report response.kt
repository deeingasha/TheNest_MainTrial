package com.example.thenest_maintrial.data.remote.model.response

//sealed class PropReportResponse {
//    data class PdfFalse(val message: String, val data: List<LL_Property>) : PropReportResponse()
//    data class PdfTrue(val status: String, val data: String, val message: String) : PropReportResponse()
//}

data class PropReportResponse(
    val data: String,
    val message: String
)
//data class PropPDFResponse(
//    val data: String,
//    val message: String
//)

//when (val response = api.createLandlordPropertiesReport(filter, sort, search, pdf)) {
//    is PropReportResponse.PdfFalse -> {
//        // Handle the response when pdf is false or not set
//    }
//    is PropReportResponse.PdfTrue -> {
//        // Handle the response when pdf is true
//    }
//}
