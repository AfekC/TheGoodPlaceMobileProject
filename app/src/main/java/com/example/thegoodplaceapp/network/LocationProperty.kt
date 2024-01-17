package com.example.thegoodplaceapp.network

import java.util.Objects

data class AddressComponents(val long_name: String, val short_name: String, val types: List<String>)
data class ResultProperty(val address_components: List<AddressComponents>)
data class LocationProperty(val results: List<ResultProperty>)
