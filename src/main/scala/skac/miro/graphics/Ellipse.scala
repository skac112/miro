package skac.miro.graphics

import skac.miro._
import skac.miro.transform._
import skac.miro.Graphic._
import skac.miro.GenericEllipse

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

}
