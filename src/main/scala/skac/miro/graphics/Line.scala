package skac.miro.graphics

import skac.miro._
import skac.miro.Graphic._
import skac.miro.transform._

/**
 * @author slawek
 */
case class Line(
  override val end: Point = Point(1, 0),
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends Graphic with GenericLine {
  override def doTransform(trans: Transform): Graphic = trans match {
    case aff: Affine => Line(aff.transPt(end), genericAttribs)
    case _ => this
  }

  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)

  override lazy val bounds = Bounds(ori, end)
}
