import kotlin.reflect.KProperty
import kotlin.test.Test
import kotlin.test.assertEquals

class TestsWeek5 {

    private val testStudent = Student(25, "Zé", StudentType.Doctoral)
    private val sqlGenerator = Week5.SQLGenerator(Week5.SQLMapping())

    @Test
    fun testCreateTable() {
        assertEquals("CREATE TABLE STUDENT (number INT NOT NULL, name CHAR NOT NULL, type ENUM(´Bachelor´, ´Master´, ´Doctoral´));", sqlGenerator.createTable(Student::class))
    }

    @Test
    fun testInsertInto() {
        assertEquals("INSERT INTO STUDENT (number, name, type) VALUES (25, ´Zé´, ´Doctoral´);", sqlGenerator.insertInto(testStudent))
    }
}

fun main() {
    val fields: List<KProperty<*>> = Student::class.dataClassFields
    println(fields)
    val isEnum = StudentType::class.isEnum
    println(isEnum)
    val enumConstants: List<StudentType> = StudentType::class.enumConstants
    println(enumConstants)
}