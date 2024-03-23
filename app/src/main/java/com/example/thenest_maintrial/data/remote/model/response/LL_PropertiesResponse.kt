package com.example.thenest_maintrial.data.remote.model.response

data class LL_PropertiesResponse (
    val data:List<LL_Property>?,
    val message: String,
)

data class LL_Property (
    val name: String,
    val location : String,
    val propertyID: String,
    val total_units: Int,
    val units:List<Prop_UnitDetails>,
    val vacant_units: Int,
)
data class Prop_UnitDetails (
    val unitCode: String,
    val unit_type : String,
    val rent: Int,
    val isOccupied: Boolean,
)

data class singlePropertyResponse (
    val data: LL_Property?,
    val message: String,
)