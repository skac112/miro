package skac

/**
 * @author slawek
 */
import miro.Point
import miro.transform._
import miro.segments._
import miro.attribs._
import miro.attribs.colors._

package object miro {
  import skac.miro.graphics._
  type PosGraphic = (Graphic, Point)
  type Ensemble = Seq[PosGraphic]
  type SegEnsemble = Seq[Segment]
  type Transform = PosGraphic => PosGraphic
  type Points = Seq[Point]
  type Subpath = (GenericSegCurve, Point)
  type Subpaths = Seq[Subpath]
  type PtSet = Set[Point]
  implicit def Tuple2Point(Tuple: Tuple2[Double, Double]) = Point(Tuple._1, Tuple._2)
  implicit def double2Point(value: Double) = Point(value, .0)
  // implicit def angle2Double(angle: Angle) = angle.normVal
  implicit def angle2Double(angle: Angle) = angle.normVal
  implicit def double2Angle(value: Double) = Angle(value)
  implicit def seg2SegCurve(seg: Segment): SegCurve = SegCurve(Seq(seg))
  implicit def seq2SegCurve(seq: Seq[Segment]) = SegCurve(seq)
  def ori = Point(0.0, 0.0)

  def ensemble(graphic: Graphic): Ensemble = graphic match {
    case Group(elements, _) => elements
    case _ => Seq((graphic, ori))
  }

  implicit def graphicToPosGraphic(graphic: Graphic) = (graphic, ori)
  implicit def posGraphicTpGraphic(posGraphic: PosGraphic): Graphic = posGraphic._1
  implicit def doubleToRot(value: Double) = Rotation(value)
  implicit def angleToRot(angle: Angle) = Rotation(angle.normVal)
  implicit def stringToCol(webStr: String) = new Color(webStr)
  implicit def tripleToCol(triple: (Double, Double, Double)) = Color(triple._1, triple._2, triple._3)
  implicit def quadToCol(quad: (Double, Double, Double, Double)) = Color(quad._1, quad._2, quad._3, quad._4)
  // implicit def double2Straight(value: Double) = skac.miro.segments.Straight(value)
  implicit def tupleToStroke(tuple: (Color, Double)) = Stroke(tuple._1, tuple._2)
  implicit def tuple2ToStroke(tuple: (String, Double)) = Stroke(new Color(tuple._1), tuple._2)
  def bounds(pg: PosGraphic): Bounds = pg._1.bounds move pg._2
  def bounds(segments: SegEnsemble): Bounds = segments.foldLeft(Bounds.empty) {_ + _.bounds}
  def subpathBounds(subpath: Subpath): Bounds = subpath._1.bounds move subpath._2
  def subpathsBounds(subpaths: Subpaths) = subpaths.foldLeft(Bounds.empty) {_ + subpathBounds(_)}
}
