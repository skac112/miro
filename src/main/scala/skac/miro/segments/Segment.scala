package skac.miro.segments

import skac.miro._
import skac.miro.transform._

trait Segment extends Curve {
  def doTransform(trans: Affine): Segment = this
  def t(trans: Affine) = doTransform(trans: Affine)
  def r(angle: Double) = doTransform(Rotation(angle))
  def bounds = Bounds.empty
}
