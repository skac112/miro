package skac.miro.graphics

import skac.miro._
import skac.miro.Graphic._
import com.github.skac112.vgutils._

/**
 * Implementacja interfejsu Path odpowiadajaca elementowi Path w SVG.
 * @author slawek
 */
trait GenericPath extends Graphic {
  def subpaths: Subpaths
  def +(subpath: GenericSegCurve, transl: Point) = Path(subpaths :+ (subpath, transl), genericAttribs)
  override def bounds = skac.miro.subpathsBounds(subpaths)
}
