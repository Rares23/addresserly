package ro.crxapps.addresserly.core.data.uitls

import java.util.*

class UniqIdGenerator {
    fun provideUniqIdFromTwoValues(): Long {
        return UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
    }
}