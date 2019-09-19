package com.github.skac112.miro.examples

import com.github.skac112.miro.graphics._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.draw.svg._
import com.github.skac112.miro.attribs._
import com.github.skac112.miro.attribs.colors._
import com.github.skac112.miro._
import com.github.skac112.vgutils._

object Example1 {
  def group = {
    val ga1 = GenericAttribs(None, Some(Stroke(MiroColor.red(), 1.0)))
    val ga = GenericAttribs(None, Some(Stroke(MiroColor.blue(), 1.0)))
    val c_pos1 = (Circle(100.0, ga1), Point(.0, .0))
    val c_pos2 = (Circle(50.0, ga1), Point(200.0, .0))
    val c = Circle(70.0, ga)
    val c_pos = c.tanTo2c(c_pos1, c_pos2, true, true)
    Group((c_pos1 :: c_pos2 :: Nil) ++ c_pos)
  }
}
