package skac.miro

import skac.miro.Graphic._

/**
 * @author slawek
 * Linia, np. sciezka (path), odcinek, luk itp.
 */
trait Curve {
  def ptAt(arcLen: Double): Point = ori
  def len = 0.0
  def closed: Boolean = false
  def end: Point = ptAt(len)
}
