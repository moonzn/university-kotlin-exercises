import kotlin.test.Test
import kotlin.test.assertEquals

class TestsWeek6 {

    private val testStudent = Student(25, "Zé", StudentType.Doctoral)
    private val sqlGenerator = SQLGenerator(SQLMapping())

    @Test
    fun testCreateTable() {
        assertEquals("CREATE TABLE STUDENT (number INT PRIMARY KEY, name VARCHAR(50) NOT NULL, degree ENUM(‘Bachelor’, ‘Master’,’Doctoral’));", sqlGenerator.createTable(Student::class))
    }

    @Test
    fun testCreateTabledOld() {
        assertEquals("CREATE TABLE STUDENT (number INT NOT NULL, name CHAR NOT NULL, type ENUM(´Bachelor´, ´Master´, ´Doctoral´));", sqlGenerator.createTable(Student::class))
    }

}