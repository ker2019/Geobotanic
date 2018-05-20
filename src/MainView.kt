
import java.awt.*
import java.text.DecimalFormat
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import kotlin.math.roundToInt

class MainView(private val frame: JFrame) : JPanel(BorderLayout()) {
    private val field = ImageView()

    private val textField = JTextField(10)
    private val answerTextLabel = JLabel("Right answer was ...%")
    private val confirmButton = JButton("Confirm")
    private val nextButton = JButton("Next")
    private val statisticsField = JTextArea().apply {
        rows = 3
        isEditable = false
        background = this@MainView.background
    }
    private val graphic = ImageView()

    private var attemptCount = 0
    private var absoluteErrorSum = 0
    private var relativeErrorSum = 0.0
    private val densites = ArrayList<Int>()
    private val userAnswers = ArrayList<Int>()

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

        val label = JLabel("What fraction of the area covered with black?")
        val preferredWidth = label.preferredSize.width
        rightTopPanel.add(label, constraints)
        rightTopPanel.add(JLabel("Enter your guess here (in %):"), constraints)

        val answerPanel = JPanel(FlowLayout(FlowLayout.LEADING))

        answerPanel.add(textField)
        answerPanel.add(confirmButton)

        rightTopPanel.add(answerPanel, constraints)

        rightTopPanel.add(answerTextLabel, constraints)

        rightTopPanel.add(nextButton, constraints)

        rightTopPanel.add(JPanel(), GridBagConstraints().apply { gridheight = GridBagConstraints.REMAINDER })

        rightPanel.add(JPanel(), BorderLayout.CENTER)

        val rightBottomPanel = JPanel(BorderLayout())
        rightPanel.add(rightBottomPanel, BorderLayout.SOUTH)

        rightBottomPanel.add(statisticsField, BorderLayout.SOUTH)

        rightBottomPanel.add(graphic, BorderLayout.CENTER)

        graphic.preferredSize = Dimension(preferredWidth, preferredWidth)

        field.image = buildImage()

        showAnswer(null)

        confirmButton.addActionListener {
            val density = (measureDensity(field.image!!) * 100).roundToInt()
            val userAnswer = textField.text.toInt()
            densites.add(density)
            userAnswers.add(userAnswer)

            val absoluteError = Math.abs(density - userAnswer)
            val relativeError = 100 * absoluteError / density.toDouble()
            absoluteErrorSum += absoluteError
            relativeErrorSum += relativeError
            attemptCount++

            statisticsField.text = buildString {
                val format = DecimalFormat("0.00")
                append("Attempts: ")
                append(attemptCount)
                appendln()
                append("Average absolute error: ")
                append(format.format(absoluteErrorSum / attemptCount.toDouble()))
                append("%")
                appendln()
                append("Average relative error: ")
                append(format.format(relativeErrorSum / attemptCount))
                append("%")
                appendln()
            }

            graphic.image = buildGraphic(densites, userAnswers, Math.min(graphic.width, graphic.height))
            showAnswer(density)
        }

        nextButton.addActionListener {
            field.image = buildImage()
            showAnswer(null)
        }

        val defaultForeground = textField.foreground
        fun validate() {
            val valid = try {
                textField.text.toInt() in 0..100
            }
            catch (e: NumberFormatException) {
                false
            }
            textField.foreground = if (valid) defaultForeground else Color.RED
            confirmButton.isEnabled = valid
        }
        validate()

        textField.document.addDocumentListener(object: DocumentListener {
            override fun changedUpdate(e: DocumentEvent) {
                validate()
            }

            override fun insertUpdate(e: DocumentEvent) {
                validate()
            }

            override fun removeUpdate(e: DocumentEvent) {
                validate()
            }

        })
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