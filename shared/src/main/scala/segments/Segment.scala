package com.github.skac112.miro.segments

import com.github.skac112.miro._
import com.github.skac112.vgutils.transform._
import com.github.skac112.vgutils._

trait Segment extends Curve {
  def doTransform(trans: Affine): Segment = this
  def t(trans: Affine) = doTransform(trans: Affine)
  def r(angle: Double) = doTransform(Rotation(angle))
  def bounds = Bounds.empty

  // TODO: uzupelnic !!!
  override def apply(t: Double) = ori
}
