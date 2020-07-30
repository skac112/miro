package com.github.skac112.miro.segments

import com.github.skac112.miro._
import com.github.skac112.vgutils._

/**
 * Generic quadratic segment. It has no control point defined because in case of smooth
 * quadratic implementation, control point is dependently determined by previous segment
 * in a path.
 * @param end
 */
abstract class GenericQuadratic(override val end: Point) extends Segment {

}
