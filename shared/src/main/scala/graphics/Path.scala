package com.github.skac112.miro.graphics

import com.github.skac112.miro._
import com.github.skac112.miro.Graphic._
import com.github.skac112.vgutils._

object Path {
  // def fromSegLine(segLine: GenericSegLine) = Path(Seq(segLine.segments), segLine.genericAttribs)

  /**
   * Sciezka z pojedyncza podsciezka
   */
  def simple(curve: GenericSegCurve, genericAttribs: GenericAttribs = defaultGenericAttribs) =
    Path(Seq((curve, ori)), genericAttribs)
}

/**
 * Implementacja interfejsu Path odpowiadajaca elementowi Path w SVG.
 * @author slawek
 */
case class Path(override val subpaths: Subpaths,
 override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericPath {
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
}
