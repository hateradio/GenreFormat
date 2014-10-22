package hateradio.genre

import java.util.{Timer, TimerTask}
import javax.swing.BorderFactory

import scala.swing._
import scala.swing.Dimension
import scala.swing.Swing.Raised
import scala.swing.event.Event

import java.awt.Color

trait SwingEvent {
  type ValueChanged = scala.swing.event.ValueChanged

  object ValueChanged {
    def unapply(x: Event) = x match {
      case vc: ValueChanged => Some(vc.source.asInstanceOf[TextArea])
      case _ => None
    }
  }

  implicit class TextAreaOps(field: TextArea) {
    def textInput(event: String => Unit): Unit = {
      field subscribe {
        case ValueChanged(tf) => event(tf.text)
        case _ =>
      }
    }
  }
}

object Pane extends SimpleSwingApplication with SwingEvent {

  val input = newText
  val output = newText
  val status = newLabel(" ")
  val clipboard = java.awt.Toolkit.getDefaultToolkit.getSystemClipboard

  def newText = new TextArea {
    text = ""
    columns = 35
    rows = 4
    lineWrap = true
    border = Swing CompoundBorder (Swing EtchedBorder Raised, Swing EmptyBorder (5, 5, 5, 5))
  }

  def newLabel(s: String) = new Label(s) {
    border = Swing.EmptyBorder(3, 0, 3, 0)
    horizontalAlignment = Alignment.Left
  }

  def emptyScroll(c: Component) = new ScrollPane(c) {
    border = Swing.EmptyBorder(0, 0, 0, 0)
  }

  private def clearStatusBar(timeout: Int = 1500) = {
    new Timer().schedule(new TimerTask() {
      def run() {
        status.text = ""
      }
    }, timeout);
  }

  private def copyButton(): Button = {
    new Button {
//      preferredSize = new Dimension(10, 10)
      action = Action("Copy") {
        val sel = new java.awt.datatransfer.StringSelection(output.text)
        clipboard.setContents(sel, null)
        status.text = "Copied"

        clearStatusBar()
      }
    }
  }

  def top = new MainFrame {
    title = "Genre Format"
    iconImage = Swing.Icon(getClass getResource "/icons/ear.png").getImage
    resizable = false

    contents = new BoxPanel(Orientation.Vertical) {
      contents += emptyScroll(newLabel("Genre text:"))
      contents += emptyScroll(input)
      contents += emptyScroll(newLabel("Result:"))
      contents += emptyScroll(output)
      contents += emptyScroll(newLabel(" "))
      contents += emptyScroll(copyButton())
      contents += emptyScroll(status)
      border = Swing.EmptyBorder(5, 8, 8, 8)
      background = new Color(237, 237, 238)
    }

    input textInput { text => output.text = Genre format text }

    setLocationRelativeTo(this)

    peer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE)
  }

}
