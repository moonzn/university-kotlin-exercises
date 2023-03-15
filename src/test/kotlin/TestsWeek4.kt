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