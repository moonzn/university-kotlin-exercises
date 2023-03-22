import kotlin.test.Test
import kotlin.test.assertEquals

class TestsWeek6SQL {

    private val testStudent = Student(25, "Zé", StudentType.Doctoral)
    private val sqlGenerator = SQLGenerator(SQLMapping())

    @Test
    fun testCreateTable() {
        assertEquals("CREATE TABLE STUDENT (number INT PRIMARY KEY, name VARCHAR(50) NOT NULL, degree ENUM(´Bachelor´, ´Master´, ´Doctoral´));", sqlGenerator.createTable(Student::class))
    }

    @Test
    fun testInsertInto() {
        assertEquals("INSERT INTO STUDENT (number, name, type) VALUES (25, ´Zé´, ´Doctoral´);", sqlGenerator.insertInto(testStudent))
    }
}