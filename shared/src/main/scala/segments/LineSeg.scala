package com.github.skac112.miro.segments

import com.github.skac112.miro._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class LineSeg(override val end: Point) extends GenericLineSeg(end) {
  override lazy val len = end.modulus
}
