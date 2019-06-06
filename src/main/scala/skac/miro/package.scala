package skac

/**
 * @author slawek
 */
import miro.segments._
import miro.attribs._
import miro.attribs.colors._
import com.github.skac112.vgutils._

package object miro {
  import skac.miro.graphics._
  type PosGraphic[+G <: Graphic] = (G, Point)
  type Ensemble = Seq[PosGraphic[Graphic]]
  type SegEnsemble = Seq[Segment]
  type MiroTransform = PosGraphic[Graphic] => PosGraphic[Graphic]
  type Points = Seq[Point]
  type Subpath = (GenericSegCurve, Point)
  type Subpaths = Seq[Subpath]
  type PtSet = Set[Point]
  implicit def Tuple2Point(Tuple: Tuple2[Double, Double]) = Point(Tuple._1, Tuple._2)
  implicit def double2Point(value: Double) = Point(value, .0)
  // implicit def angle2Double(angle: Angle) = angle.normVal
//  implicit def angle2Double(angle: Angle) = angle.normVal
//  implicit def double2Angle(value: Double) = Angle(value)
  implicit def seg2SegCurve(seg: Segment): SegCurve = SegCurve(Seq(seg))
  implicit def seq2SegCurve(seq: Seq[Segment]) = SegCurve(seq)

  def ensemble(graphic: Graphic): Ensemble = graphic match {
    case Group(elements, _) => elements
    case _ => Seq((graphic, ori))
  }

  implicit def graphicToPosGraphic[G <: Graphic](graphic: G) = (graphic, ori)
  implicit def posGraphicTpGraphic[G <: Graphic](posGraphic: PosGraphic[G]): G = posGraphic._1
  implicit def posGraphicToRichPosGraphic[G <: Graphic](posGraphic: PosGraphic[G]) = RichPosGraphic[G](posGraphic)
  implicit def richPosGraphicToPosGraphic[G <: Graphic](rpg: RichPosGraphic[G]) = rpg.pg
  implicit def stringToCol(webStr: String) = new MiroColor(webStr)
  implicit def tripleToCol(triple: (Double, Double, Double)) = new MiroColor(triple._1, triple._2, triple._3)
  implicit def quadToCol(quad: (Double, Double, Double, Double)) = new MiroColor(quad._1, quad._2, quad._3, quad._4)
  // implicit def double2Straight(value: Double) = skac.miro.segments.Straight(value)
  implicit def tupleToStroke(tuple: (MiroColor, Double)) = Stroke(tuple._1, tuple._2)
  implicit def tuple2ToStroke(tuple: (String, Double)) = Stroke(new MiroColor(tuple._1), tuple._2)
  def bounds(pg: PosGraphic[Graphic]): Bounds = pg._1.bounds move pg._2
  def bounds(segments: SegEnsemble): Bounds = segments.foldLeft(Bounds.empty) {_ + _.bounds}
  def subpathBounds(subpath: Subpath): Bounds = subpath._1.bounds move subpath._2
  def subpathsBounds(subpaths: Subpaths) = subpaths.foldLeft(Bounds.empty) {_ + subpathBounds(_)}
}
