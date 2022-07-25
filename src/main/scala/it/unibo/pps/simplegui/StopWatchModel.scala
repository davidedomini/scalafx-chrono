package it.unibo.pps.simplegui

import javafx.concurrent as jfxc
import org.scalafx.extras.*
import org.scalafx.extras.mvcfx.ModelFX
import scalafx.Includes.*
import scalafx.beans.property.{LongProperty, ReadOnlyBooleanProperty, ReadOnlyBooleanWrapper}

/**
 * StopWatch behaviour ModelFX.
 */
class StopWatchModel extends ModelFX {

  private val _running = ReadOnlyBooleanWrapper(false)

  val running: ReadOnlyBooleanProperty = _running.readOnlyProperty

  private val counterService = new CounterService()
  counterService.period = 10.ms

  val minutes        = new LongProperty()
  val seconds        = new LongProperty()
  val secondFraction = new LongProperty()

  counterService.elapsedTime.onChange { (_, _, newValue) =>
    val t = newValue.longValue()
    secondFraction.value = t   % 1000
    seconds.value = (t / 1000) % 60
    minutes.value = t / 1000 / 60
  }

  def onStart(): Unit = {
    counterService.doResume()
    _running.value = true
  }

  def onStop(): Unit = {
    counterService.doPause()
    _running.value = false
  }

  def onReset(): Unit = {
    counterService.doReset()
  }

  private class CounterService extends jfxc.ScheduledService[Long] {

    private var timeAccumulator: Long = 0
    private var restartTime: Long     = 0

    val elapsedTime = new LongProperty()

    override def createTask(): jfxc.Task[Long] = {
      new jfxc.Task[Long]() {

        override protected def call(): Long = {
          val ct = System.currentTimeMillis()
          val et = timeAccumulator + (ct - restartTime)
          onFX {
            elapsedTime.value = et
          }
          et
        }
      }
    }

    def doPause(): Unit = {
      val ct = System.currentTimeMillis()
      timeAccumulator += (ct - restartTime)
      onFX {
        elapsedTime.value = timeAccumulator
      }
      this.cancel()
    }

    def doResume(): Unit = {
      restartTime = System.currentTimeMillis()
      this.restart()
    }

    def doReset(): Unit = {
      timeAccumulator = 0
      onFX {
        elapsedTime.value = 0
      }
    }
  }

}