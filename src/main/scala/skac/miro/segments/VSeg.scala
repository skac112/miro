package skac.miro.segments

import skac.miro._

/**
 * Segment - pionowy odcinek
 */
case class VSeg(override val len: Double) extends GenericLineSeg(Point(.0, len)) {
  // override lazy val end = Point(.0, len)
}
