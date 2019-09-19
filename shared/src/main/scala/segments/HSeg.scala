package com.github.skac112.miro.segments

import com.github.skac112.miro._
import com.github.skac112.vgutils._

/**
 * Segment - poziomy odcinek
 */
case class HSeg(override val len: Double) extends GenericLineSeg(Point(len, .0)) {
  // override lazy val end = Point(.0, len)
}
