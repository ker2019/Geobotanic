import java.awt.Color
import java.awt.image.BufferedImage

fun measureDensity(image: BufferedImage): Double {
    var x = 0
    var y = 0
    var countOfBlackPoints = 0
    val width = image.getWidth()
    val height = image.getHeight()

    for (x in 0 until width)
        for (y in 0 until height)
            if (image.getRGB(x, y) == Color.BLACK.rgb) countOfBlackPoints++
    return countOfBlackPoints.toDouble() / (height * width)
}