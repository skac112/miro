package skac.miro.graphics

import skac.miro._
import skac.miro.Graphic._

object Path {
  // def fromSegLine(segLine: GenericSegLine) = Path(Seq(segLine.segments), segLine.genericAttribs)

  /**
   * Sciezka z pojedyncza podsciezka
   */
  def simple(curves: GenericSegCurve, genericAttribs: GenericAttribs = defaultGenericAttribs) =
    Path(Seq((curves, ori)), genericAttribs)
}

/**
 * Implementacja interfejsu Path odpowiadajaca elementowi Path w SVG.
 * @author slawek
 */
case class Path(override val subpaths: Subpaths, override val genericAttribs: GenericAttribs = defaultGenericAttribs) extends GenericPath {
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
}
