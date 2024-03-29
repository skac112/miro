package com.github.skac112.miro.graphics

import com.github.skac112.miro._
import com.github.skac112.miro.Graphic._
import com.github.skac112.vgutils._
import com.github.skac112.vgutils.transform._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class Line[+D](
                 override val end: Point = Point(1, 0),
                 override val genericAttribs: GenericAttribs = defaultGenericAttribs,
                 override val metadataO: Option[D] = None)
extends Graphic[D] with GenericLine {
  override def doTransform(trans: MiroTransform): Graphic[D] = trans match {
    case aff: Affine => Line(aff.transPt(end), genericAttribs)
    case _ => this
  }

  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)

  override lazy val bounds = Bounds(ori, end)
}
