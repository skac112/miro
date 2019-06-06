package skac.miro

import graphics._
import attribs._
import segments._
import com.github.skac112.vgutils._

object GenericSegCurve {
  type CurveTrans = Curve => Curve
  // type LineEnsTrans = LineEnsemble => LineEnsemble
}

/**
 * Linia skladajaca sie z segmentow, z ktorych kazdy jest linia. Poczatek kazdej
 * kolejnej linii jest tozsamy z koncem poprzedniej.
 * @author slawek
 */
trait GenericSegCurve extends Curve {
  // import GenericPath._
  def segments: SegEnsemble
  def ++(other: GenericSegCurve) = SegCurve(segments ++ other.segments)
  def +(other: Segment) = SegCurve(segments :+ other)
  def bounds = segments.foldLeft((Bounds.empty, ori)) {
    case ((curr_b, curr_p), seg) => (curr_b + (seg.bounds move curr_p), curr_p + seg.end)
  }._1

  /**
   * Zwraca sciezke powstala przez dodanie do tej sciezki segmentu bedacego wynikiem dzialania funkcji transFun
   * na ostatni segment biezacej sciezki
   */
  // def transJoin(transFun: LineTrans) = Path(segments :+ transFun(segments.last), genericAttribs)
  // def +> = transJoin _
  // def join(line: Line) = Path(segments :+ line, genericAttribs)
  // def transElems(transFun: LineEnsTrans) = Path(transFun(segments), genericAttribs)
}
