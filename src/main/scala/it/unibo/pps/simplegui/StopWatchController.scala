package it.unibo.pps.simplegui

import javafx.fxml as jfxf
import javafx.scene.control as jfxsc
import org.scalafx.extras.mvcfx.ControllerFX
import scalafx.Includes.*

/**
 * StopWatch UI view controller. It is intended to create bindings between UI definition loaded fro FXML configuration
 * and the UI model
 */
class StopWatchController(model: StopWatchModel) extends ControllerFX:

  @jfxf.FXML
  private var minutesLabel: jfxsc.Label = _
  @jfxf.FXML
  private var secondsLabel: jfxsc.Label = _
  @jfxf.FXML
  private var fractionLabel: jfxsc.Label = _
  @jfxf.FXML
  private var startButton: jfxsc.Button = _
  @jfxf.FXML
  private var stopButton: jfxsc.Button = _
  @jfxf.FXML
  private var resetButton: jfxsc.Button = _

  override def initialize(): Unit =
    minutesLabel.text.value = format2d(model.minutes.longValue)
    model.minutes.onChange { (_, _, v) => minutesLabel.text.value = format2d(v.longValue) }
    secondsLabel.text.value = format2d(model.seconds.longValue())
    model.seconds.onChange { (_, _, v) => secondsLabel.text.value = format2d(v.longValue()) }
    fractionLabel.text.value = format2d(model.secondFraction.longValue() / 10)
    model.secondFraction.onChange { (_, _, v) => fractionLabel.text.value = format2d(v.longValue() / 10) }

    startButton.disable <== model.running
    stopButton.disable <== !model.running
    resetButton.disable <== model.running

    startButton.onAction = () => model.onStart()
    stopButton.onAction = () => model.onStop()
    resetButton.onAction = () => model.onReset()

  private def format2d(t: Number) = f"${t.longValue()}%02d"