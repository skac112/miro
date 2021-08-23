package com.github.skac112.miro.graphics

import com.github.skac112.miro.Graphic
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.GenericPolygon
import com.github.skac112.miro.GenericRect
import com.github.skac112.vgutils.{Point, ori}

//object Rect {
//  def unapply(rect: Rect) = Some((rect.width, rect.height, rect.genericAttribs))
//}

/**
 * Rectangle.
 * @author slawek
 */
case class Rect[+D](
  override val width: Double = 1.0,
  override val height: Double = 1.0,
  override val rotation: Double = 0.0,
  override val genericAttribs: GenericAttribs = defaultGenericAttribs,
  override val metadataO: Option[D] = None)
extends GenericRect with Graphic[D] {
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)

  override def characteristicPt(code: Symbol) = code match {
    case 'TL => tl
    case 'TR => tr
    case 'BL => bl
    case 'BR => br
    case 'T => t
    case 'R => r
    case 'B => b
    case 'L => l
    case 'C => c
    case _ => super.characteristicPt(code)
  }

  // TODO: uzupelnic !!!
  override def apply(t: Double) = ori

  override def hitTest(pt: Point) = {
    val rot_pt = pt.rot(-rotation)
    Some(rot_pt.x >= 0 && rot_pt.x <= width && rot_pt.y >= 0 && rot_pt.y <= height)
  }
}
