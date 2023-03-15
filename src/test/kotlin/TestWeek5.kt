import kotlin.test.Test
import kotlin.test.assertEquals
class TestWeek5 {

    @Test
    fun testCreateTable() {
        assertEquals("CREATE TABLE Student (number INT NOT NULL, name CHAR NOT NULL, type ENUM(´Bachelor´, ´Master´, ´Doctoral´));", createTable(Student::class))
    }
}