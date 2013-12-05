package com.github.hateradio.GenreFormat

import scala.swing._
import scala.swing.Swing.Raised

import java.awt.Color
import scala.swing.event.Event
import scala.Some

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

  def newText = new TextArea {
    text = ""
    columns = 35
    rows = 4
    lineWrap = true
    border = Swing EtchedBorder Raised
  }

  def newLabel(s: String) = new Label(s) {
    border = Swing.EmptyBorder(3, 0, 3, 0)
    horizontalAlignment = Alignment.Left
  }

  def emptyScroll(c: Component) = new ScrollPane(c) {
    border = Swing.EmptyBorder(0, 0, 0, 0)
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
      border = Swing.EmptyBorder(5, 8, 8, 8)
      background = new Color(237, 237, 238)
    }

    input textInput { text => output.text = Genre format text }

    setLocationRelativeTo(this)
  }

}