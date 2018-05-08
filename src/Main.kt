
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants

fun main(args: Array<String>) {
    JFrame("Geobotanic").apply {
        contentPane = MainView(this).apply { isOpaque = false }
        size = Dimension(1024, 768)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isVisible = true
    }
}