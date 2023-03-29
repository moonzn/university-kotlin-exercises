import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestsWeek7 {

    val dirA = DirectoryElement("dirA")
    val f1 = FileElement("f1.kt", dirA)
    val f2 = FileElement("f2.kt", dirA)
    val dirB = DirectoryElement("dirB", dirA)
    val f3 = FileElement("f3.txt", dirB)
    val f4 = FileElement("f4.json", dirB)
    val dirC = DirectoryElement("dirC", dirA)
    val f5 = FileElement("f5.exe", dirC)
    val f6 = FileElement("f6.kt", dirC)

    @Test
    fun testSearchForFile() {
    }




}