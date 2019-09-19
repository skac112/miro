package com.github.skac112.miro.examples

import com.github.skac112.miro.graphics._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.draw.svg._
import com.github.skac112.miro.attribs._
import com.github.skac112.miro.attribs.colors._
import com.github.skac112.miro._
import com.github.skac112.miro.graphics.compounds._
import scala.math._
import com.github.skac112.vgutils._

/**
 * Example - triangle.
 */
object ExampleTriangle {
  def group = {
    val ga = GenericAttribs(Some(MiroColor.red()), None)
    val triangle = (Triangle((100.0, .0), (50.0, 50.0), ga), Point(100.0, 100.0))
    Group(triangle :: Nil)
  }
}
