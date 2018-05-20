import java.awt.Color
import java.awt.image.BufferedImage

fun buildGraphic(densites: ArrayList<Int>, userAnsweres: ArrayList<Int>, size: Int): BufferedImage {
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    val g = image.createGraphics()

    g.color = Color.WHITE
    g.fillRect(0, 0, image.width, image.height)

    g.color = Color.BLACK
    g.drawLine(0, size, size, 0)

    for ((density, answer) in densites.zip(userAnsweres)) {
        g.fillOval(density * size / 100, size - answer * size / 100, 10, 10)
    }

    g.dispose()

    return image
}