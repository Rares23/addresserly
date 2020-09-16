package ro.crxapps.addresserly.core.data.uitls

class UniqIdGenerator {
    fun provideUniqIdFromTwoValues(firstValue: Double, secondValue: Double): Long {
        var uniqueId: Long = firstValue.toLong()
        uniqueId = uniqueId shl 32
        uniqueId += secondValue.toLong()
        return uniqueId
    }
}