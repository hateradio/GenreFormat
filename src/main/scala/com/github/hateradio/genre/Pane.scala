package com.github.hateradio.GenreFormat

import swing._
import scala.swing.Swing.Raised

import javax.swing.event.{DocumentEvent, DocumentListener}
import java.awt.Color


object Pane extends SimpleSwingApplication {

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
    iconImage = Swing.Icon(getClass.getResource("/icons/ear.png")).getImage
    resizable = false

    contents = new BoxPanel(Orientation.Vertical) {
      contents += emptyScroll(newLabel("Genre text:"))
      contents += emptyScroll(input)
      contents += emptyScroll(newLabel("Result:"))
      contents += emptyScroll(output)
      border = Swing.EmptyBorder(5, 8, 8, 8)
      background = new Color(237, 237, 238)
    }

    attachListener
    setLocationRelativeTo(this)
  }

  private def attachListener = {
    input.peer.getDocument.addDocumentListener(new DocumentListener() {
      def changedUpdate(e: DocumentEvent) = updateProperty

      def insertUpdate(e: DocumentEvent) = updateProperty

      def removeUpdate(e: DocumentEvent) = updateProperty

      def updateProperty = output.text = Genre.format(input.text)
    })
  }
}