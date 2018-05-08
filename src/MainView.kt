import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class MainView(private val frame: JFrame) : JPanel(BorderLayout()) {
    private val field = Field()

    private val textField = JTextField(10)
    private val answerTextLabel = JLabel("Right answer was ...%")
    private val confirmButton = JButton("Confirm")
    private val nextButton = JButton("Next")

    init {
        add(field, BorderLayout.CENTER)

        val rightPanel = JPanel(BorderLayout())
        add(rightPanel, BorderLayout.EAST)
        val rightTopPanel = JPanel(GridBagLayout())
        rightPanel.add(rightTopPanel, BorderLayout.NORTH)

        val constraints = GridBagConstraints().apply {
            gridx = 0
            anchor = GridBagConstraints.LINE_START
        }

        rightTopPanel.add(JLabel("What fraction of the area covered with black?"), constraints)
        rightTopPanel.add(JLabel("Enter your guess here (in %):"), constraints)

        val answerPanel = JPanel(FlowLayout(FlowLayout.LEADING))

        answerPanel.add(textField)
        answerPanel.add(confirmButton)

        rightTopPanel.add(answerPanel, constraints)

        rightTopPanel.add(answerTextLabel, constraints)

        rightTopPanel.add(nextButton, constraints)

        rightTopPanel.add(JPanel(), GridBagConstraints().apply { gridheight = GridBagConstraints.REMAINDER })

        rightPanel.add(JPanel(), BorderLayout.CENTER)

        field.image = buildImage()

        showAnswer(null)

        confirmButton.addActionListener {
            val density = measureDensity(field.image!!)
            showAnswer((density * 100).toInt())
        }

        nextButton.addActionListener {
            field.image = buildImage()
            showAnswer(null)
        }
    }

    private fun showAnswer(answer: Int?) {
        textField.isEnabled = (answer == null)
        confirmButton.isEnabled = (answer == null)
        answerTextLabel.isVisible = (answer != null)
        nextButton.isVisible = (answer != null)
        if (answer != null) {
            answerTextLabel.text = "Right answer was $answer%"
            frame.rootPane.defaultButton = nextButton
        }
        else {
            textField.text = ""
            frame.rootPane.defaultButton = confirmButton
        }
    }


}