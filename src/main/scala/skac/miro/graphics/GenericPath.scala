package skac.miro.graphics

import skac.miro._
import skac.miro.Graphic._

/**
 * Implementacja interfejsu Path odpowiadajaca elementowi Path w SVG.
 * @author slawek
 */
trait GenericPath extends Graphic {
  def subpaths: Seq[GenericSegCurve]
  def addSubpath(subpath: GenericSegCurve) = Path(subpaths :+ subpath, genericAttribs)  
}
