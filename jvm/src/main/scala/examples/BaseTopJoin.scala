package com.github.skac112.miro.examples

import com.github.skac112.miro.Graphic.GenericAttribs
import com.github.skac112.miro.attribs.Stroke
import com.github.skac112.miro.draw.svg.Draw
import com.github.skac112.miro.graphics.compounds.Joins
import com.github.skac112.miro.graphics.{Circle, Group}
import com.github.skac112.vgutils.{Color, Point}

class BaseTopJoin {
  val ga = GenericAttribs(fillO=None, strokeO=Some(Stroke(Color.black, 0.1)))
  val circ_c = Point(10, 10)
  val circ_r = 20
  val circle = (Circle(circ_r, ga), circ_c)
  val p1 = Point(11, 5)
  val p2 = Point(9, 5)
  val join = Joins.baseTop(circ_c, circ_r, p1, p2, 1, 1, ga)
  val group = Group(Seq(circle, join))
  val draw = new Draw()
  draw.saveToFile(group, "varia/example_drawings/base_top_join.svg")
}
