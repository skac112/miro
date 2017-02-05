package skac.miro.segments

import skac.miro._
import skac.miro.transform._

/**
 * Trait bazowy dla segmentu odcinkowego oraz segmentów odcinków pionowego i
 * poziomego.
 */
abstract class GenericLineSeg(override val end: Point) extends Segment {
  override def doTransform(trans: Affine) = LineSeg(trans.transPt(end) - trans.transPt(ori))
}
