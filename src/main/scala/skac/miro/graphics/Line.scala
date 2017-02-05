package skac.miro.graphics

import skac.miro._
import skac.miro.Graphic._
import skac.miro.transform._

/**
 * @author slawek
 */
case class Line(
  override val end: Point,
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends Graphic with GenericLine {
  override def doTransform(trans: Transform): Graphic = trans match {
    case aff: Affine => Line(aff.transPt(end), genericAttribs)
    case _ => this
  }
}
