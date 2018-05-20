
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JComponent

class ImageView : JComponent() {
    var image: BufferedImage? = null
        set(value) {
            field = value
            repaint()
        }

    override fun paintComponent(g: Graphics) {
        if (image != null) {
            g.drawImage(image, 0, 0, width, height, this)
        }
        else {
            g.color = Color.WHITE
        }
    }
}