package it.unibo.pps.simplegui

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.scene.layout.BorderPane

/**
 * StopWatchApp is an application illustrating use of [[org.scalafx.extras.mvcfx ModelFX-ControllerFX]] pattern,
 * where layout of the UI is loaded from FXML definition and behaviour is defined in a model.
 */
object StopWatchApp extends JFXApp3 {

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "StopWatch"
      scene = new Scene {
        root = new BorderPane {
          center = new StopWatch().view
        }
      }
    }
  }
}