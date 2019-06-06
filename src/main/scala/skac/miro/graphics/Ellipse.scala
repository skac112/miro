package skac.miro.graphics

import skac.miro._
import skac.miro.Graphic._
import skac.miro.GenericEllipse
import com.github.skac112.vgutils._

//object Ellipse {
//  def unapply(ellipse: Ellipse) = Some((ellipse.rx, ellipse.ry, ellipse.genericAttribs))
//}

/**
 * Elipsa. Polozenie srodka ma znaczenie tylko wewnątrz grupy, więc nie jest częścią danych obiektu.
 * @author slawek
 */
case class Ellipse(
  override val rx: Double = 1.0,
  override val ry: Double = 1.0,
  override val rotation: Double = 0.0,
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericEllipse with Graphic {
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)

  override def bounds: Bounds = ???
}
