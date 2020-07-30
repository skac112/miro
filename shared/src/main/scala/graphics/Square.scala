package com.github.skac112.miro.graphics

import com.github.skac112.miro.GenericRect
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro._

/**
 * @author slawek
 */
case class Square(size: Double = 1.0,
  override val rotation: Double = 0.0,
  override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericRect with Graphic {
  override val width = size
  override val height = size
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
}
