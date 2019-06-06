package skac.miro.graphics

import skac.miro._
import skac.miro.Graphic._
import com.github.skac112.vgutils._
import com.github.skac112.vgutils.transform._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class Line(
                 override val end: Point = Point(1, 0),
                 override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends Graphic with GenericLine {
  override def doTransform(trans: MiroTransform): Graphic = trans match {
    case aff: Affine => Line(aff.transPt(end), genericAttribs)
    case _ => this
  }

  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)

  override lazy val bounds = Bounds(ori, end)
}
