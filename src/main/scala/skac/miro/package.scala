package skac

/**
 * @author slawek
 */
import skac.miro.Point
import skac.miro.transform._
import skac.miro.segments._

package object miro {
  import skac.miro.graphics._
  type PosGraphic = (Graphic, Point)
  type Ensemble = Seq[PosGraphic]
  type SegEnsemble = Seq[Segment]
  type Transform = PosGraphic => PosGraphic
  type Points = Seq[Point]
  type Subpath = (GenericSegCurve, Point)
  type Subpaths = Seq[Subpath]
  implicit def Tuple2Point(Tuple: Tuple2[Double, Double]) = Point(Tuple._1, Tuple._2)
  implicit def double2Point(value: Double) = Point(value, .0)
  // implicit def angle2Double(angle: Angle) = angle.normVal
  implicit def angle2Double(angle: Angle) = angle.value
  implicit def double2Angle(value: Double) = Angle(value)
  implicit def seg2SegCurve(seg: Segment): SegCurve = SegCurve(Seq(seg))
  implicit def seq2SegCurve(seq: Seq[Segment]) = SegCurve(seq)
  def ori = Point(0.0, 0.0)

  def ensemble(graphic: Graphic): Ensemble = graphic match {
    case Group(elements, _) => elements
    case _ => Seq((graphic, ori))
  }

  implicit def graphic2PosGraphic(graphic: Graphic) = (graphic, ori)
  implicit def posGraphic2Graphic(posGraphic: PosGraphic): Graphic = posGraphic._1
  implicit def double2Rot(value: Double) = Rotation(value)
  implicit def angle2Rot(angle: Angle) = Rotation(angle.normVal)
  // implicit def double2Straight(value: Double) = skac.miro.segments.Straight(value)
}
