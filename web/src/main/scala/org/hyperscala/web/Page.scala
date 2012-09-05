package org.hyperscala.web

import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import org.hyperscala.html.attributes.Method
import org.powerscala.bus.Bus
import org.powerscala.Priority
import org.powerscala.event.Listenable

/**
 * @author Matt Hicks <mhicks@powerscala.org>
 */
trait Page extends Listenable {
  override val bus = new Bus(Priority.Normal)
  Bus.current = bus

  private var disposed = false

  def service(method: Method, request: HttpServletRequest, response: HttpServletResponse): Unit

  /**
   * Called before service to see if this page should still be in service.
   *
   * Defaults to false.
   */
  def shouldDispose(scope: Scope, method: Method, request: HttpServletRequest): Boolean = {
    false
  }

  def dispose() = {
    disposed = true
    bus.clear()
  }

  def isDisposed = disposed
}
