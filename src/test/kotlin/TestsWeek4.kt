import kotlin.test.Test
import kotlin.test.assertEquals

class TestsWeek4 {

    private val root = Week4.DirectoryElement("root")
    private val files = Week4.DirectoryElement("files", root)
    private val screenshots = Week4.DirectoryElement("screenshots", files)
    private val screenshot1 = Week4.FileElement("Screenshot1.png", screenshots)
    private val screenshot2 = Week4.FileElement("Screenshot2.png", screenshots)
    private val screenshot3 = Week4.FileElement("Screenshot3.png", screenshots)
    private val videos = Week4.DirectoryElement("videos", files)

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

    @Test
    fun pathTest() {
        assertEquals("C:/root", root.path)
        assertEquals("C:/root/files", files.path)
        assertEquals("C:/root/files/screenshots", screenshots.path)
        assertEquals("C:/root/files/screenshots/Screenshot1.png", screenshot1.path)
        assertEquals("C:/root/files/screenshots/Screenshot2.png", screenshot2.path)
        assertEquals("C:/root/files/screenshots/Screenshot3.png", screenshot3.path)
        assertEquals("C:/root/files/videos", videos.path)
    }

    @Test
    fun toTextTest() {
        assertEquals("\nroot\n", root.toText)
        assertEquals("\nroot\n\tfiles\n", files.toText)
        assertEquals("\nroot\n\tfiles\n\t\tscreenshots\n", screenshots.toText)
        assertEquals("\nroot\n\tfiles\n\t\tscreenshots\n\t\t\tScreenshot1.png\n", screenshot1.toText)
        assertEquals("\nroot\n\tfiles\n\t\tvideos\n", videos.toText)
    }

    @Test
    fun testPrints() {
        print(screenshot2.toText)
    }
}