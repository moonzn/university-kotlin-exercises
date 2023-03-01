import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Tests {

    @Test
    fun sumRangeTest() {
        assertEquals(6, sumRangeRec(1, 3))
        assertEquals(15, sumRangeRec(1, 5))
        assertEquals(55, sumRangeRec(1, 10))
    }

    @Test
    fun testLambda() {
        assertTrue(isEven(6))
        assertFalse(isEven(7))
        assertTrue(isOdd(15))
        assertFalse(isOdd(6))
        assertTrue(isDivisor(10)(5))
        assertFalse(isDivisor(10)(7))
    }

    @Test
    fun sumRangePredicate() {
        assertEquals(6, sumRange(1, 5) {isEven(it)})
        assertEquals(9, sumRange(1, 5) {isOdd(it)})
        assertEquals(8, sumRange(1, 5, isDivisor(10)))
        //assertEquals(8, sumRange(1, 5))
    }

    @Test
    fun perfectTest() {
        assertTrue(isPerfect(6))
        assertEquals(listOf(6, 28, 496, 8128), (1..10000).filter { n -> isPerfect(n) })
    }
}