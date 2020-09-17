package ro.crxapps.addresserly.core.data.uitls

import ro.crxapps.addresserly.locations.data.models.AddressLocation
import java.math.BigInteger
import java.security.MessageDigest


class UniqIdGenerator {
    fun provideUniqIdForAddressLocation(location: AddressLocation): Long {
        val data = "${location.address}-${location.label}-${location.lat}-${location.lng}-${location.image}"
        return encodeMD5(data)
    }

    private fun encodeMD5(valueToEncode: String): Long {
        val md5 = MessageDigest.getInstance("MD5")
        md5.update(valueToEncode.toByteArray(), 0, valueToEncode.length)

        return BigInteger(1, md5.digest()).toLong()
    }
}