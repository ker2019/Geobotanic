import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*

val roundsCount = 100000
val averageRadius = 10
val radiusDiviasion = 15

fun buildImage(): BufferedImage? {
    val random = Random()
    val image = BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB)
    val g = image.createGraphics()
    val targetDensity = random.nextDouble()

    g.color = Color.WHITE
    g.fillRect(0, 0, image.width, image.height)

    repeat(roundsCount) {
        val u = random.nextDouble()
        val v = random.nextDouble()
        val r = averageRadius + radiusDiviasion * (Math.sqrt(-2 * Math.log(u)) * Math.cos(2 * Math.PI * v)).toInt()
        if (r > 0) {
            g.color = if (random.nextDouble() < targetDensity) Color.BLACK else Color.WHITE
            g.fillOval(random.nextInt(image.width), random.nextInt(image.height), r, r)
        }
    }

    g.dispose()
    return image
}