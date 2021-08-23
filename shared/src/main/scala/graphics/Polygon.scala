package com.github.skac112.miro.graphics

import com.github.skac112.miro.GenericPolygon
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro._
import com.github.skac112.vgutils._

/**
 * @author slawek
 */
case class Polygon[+D](
                    override val points: Seq[Point],
                    override val genericAttribs: GenericAttribs = defaultGenericAttribs,
                    override val metadataO: Option[D] = None)
extends GenericPolygon with Graphic[D] {
  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)
}
