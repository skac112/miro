package com.github.skac112.miro.examples

import com.github.skac112.miro.graphics._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.draw.svg._
import com.github.skac112.miro.attribs._
import com.github.skac112.miro.attribs.colors._
import com.github.skac112.miro._
import com.github.skac112.miro.graphics.compounds._
import com.github.skac112.vgutils.Point

import scala.math._

/**
 * Example - ring.
 */
object SimpleShapes4 {
  def group = {
    val ga = GenericAttribs(Some(MiroColor.red()), None)
    val ring = (ArcSection[Any](50, 0, 0.25 * Pi, ga), Point(0.0, 0.0))
    Group(Seq[PosGraphic[Graphic[Any], Any]](ring))
  }
}
