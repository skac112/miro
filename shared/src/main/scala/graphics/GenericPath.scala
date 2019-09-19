package com.github.skac112.miro.graphics

import com.github.skac112.miro._
import com.github.skac112.miro.Graphic._
import com.github.skac112.vgutils._

/**
 * Implementacja interfejsu Path odpowiadajaca elementowi Path w SVG.
 * @author slawek
 */
trait GenericPath extends Graphic {
  def subpaths: Subpaths
  def +(subpath: GenericSegCurve, transl: Point) = Path(subpaths :+ (subpath, transl), genericAttribs)
  override def bounds = subpathsBounds(subpaths)
}
