package com.github.skac112.miro.graphics

import com.github.skac112.miro.GenericPolygon
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class Polygon(
                    override val points: Seq[Point],
                    override val genericAttribs: GenericAttribs = defaultGenericAttribs)
extends GenericPolygon with Graphic {
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
}
