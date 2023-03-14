import kotlin.test.Test
import kotlin.test.assertEquals

class TestsWeek4 {

    val root = DirectoryElement("root")
    val files = DirectoryElement("files", root)
    val screenshots = DirectoryElement("screenshots", files)
    val screenshot1 = FileElement("Screenshot1.png", screenshots)
    val screenshot2 = FileElement("Screenshot2.png", screenshots)
    val screenshot3 = FileElement("Screenshot3.png", screenshots)
    val videos = DirectoryElement("videos", files)

    @Test
    fun depthTest() {
        assertEquals(0, root.depth)
        assertEquals(1, files.depth)
        assertEquals(2, screenshots.depth)
        assertEquals(3, screenshot1.depth)
        assertEquals(3, screenshot2.depth)
        assertEquals(3, screenshot3.depth)
        assertEquals(2, videos.depth)
    }

    @Test
    fun deepElementCountTest() {
        assertEquals(6, root.deepElementCount)
        assertEquals(5, files.deepElementCount)
        assertEquals(3, screenshots.deepElementCount)
        assertEquals(0, videos.deepElementCount)
    }




}