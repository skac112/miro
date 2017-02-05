package skac.miro.segments

import skac.miro._

/**
 * Segment - poziomy odcinek
 */
case class HSeg(override val len: Double) extends GenericLineSeg(Point(len, .0)) {
  // override lazy val end = Point(.0, len)
}
