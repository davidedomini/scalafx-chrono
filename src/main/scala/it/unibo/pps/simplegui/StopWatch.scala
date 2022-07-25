package it.unibo.pps.simplegui

import org.scalafx.extras.mvcfx.MVCfx

class StopWatch(val model: StopWatchModel = new StopWatchModel())
  extends MVCfx[StopWatchController]("StopWatch.fxml"):

  def controllerInstance: StopWatchController = new StopWatchController(model)