package com.github.skac112.miro.segments

import com.github.skac112.miro._
import com.github.skac112.vgutils._

/**
 * Segment - pionowy odcinek
 */
case class VSeg(override val len: Double) extends GenericLineSeg(Point(.0, len)) {
  // override lazy val end = Point(.0, len)
}
