package com.github.skac112.miro

import scala.math._
import graphics._
import com.github.skac112.miro.Graphic._
import draw.svg._
import attribs._
import com.github.skac112.miro.attribs.colors.MiroColor._
import examples._
import com.github.skac112.miro.segments._
import com.github.skac112.miro.Dsl._
import com.github.skac112.vgutils._
import com.github.skac112.miro.attribs.colors.MiroColor
import com.github.skac112.miro.graphics.Group

object Main extends App {
  val col1 = new MiroColor(.1,.1,.0)
  val circles = (0 to 10) map {i =>
    val color = col1.addL(i/20.0)
    println(color)
    (C(100) fill color toC, Point(i*200, 0))
  }
  val g = Group(circles)
  val draw = new Draw()
  draw.saveToFile(g, "colors.svg")
  Runtime.getRuntime.exec("eog colors.svg")

  // val group = ExampleQuad.group
  // val draw = new Draw()
  // draw.saveToFile(group, "quad.svg")
  // Runtime.getRuntime.exec("eog quad.svg")
  // val c1 = Circle(100.0) strCol red() fill transparent toC
  // // val c1 = Circle(100.0) fill red() toC
  // val pc1 = Point(100.0, 100.0)
  // val c1p = (c1, pc1)
  // val ang1 = random*2.0*Pi
  // val c2 = Circle(40.0) strCol blue() fill transparent toC
  // val pc2 = pc1 + ((c1.r - c2.r) rot ang1)
  // val c2p = (c2, pc2)
  // val r3 = 20.0
  // val tan_circles = cTanTo2c(pc1.x, pc1.y, c1.r, false, pc2.x, pc2.y, c2.r, true, r3)
  // val c3 = Circle(r3) strCol green() fill transparent toC
  // val pc3 = Point((tan_circles.toSeq)(0)._1, (tan_circles.toSeq)(0)._2)
  // val c3p = (c3, pc3)
  // val tan_circles2 = cTanTo3c(pc1.x, pc1.y, c1.r, false, pc2.x, pc2.y, c2.r, true,
  //  pc3.x, pc3.y, c3.r, true)
  // val pc4 = Point((tan_circles2.toSeq)(0)._1, (tan_circles2.toSeq)(0)._2)
  // val c4 = Circle((tan_circles2.toSeq)(0)._3) strCol green(.5) fill transparent toC
  // val c4p = (c4, pc4)
  // val pc5 = Point((tan_circles2.toSeq)(1)._1, (tan_circles2.toSeq)(1)._2)
  // val c5 = Circle((tan_circles2.toSeq)(1)._3) strCol green(.5) fill transparent toC
  // val c5p = (c5, pc5)
  // val group = Group(Seq(c1p, c2p, c3p, c4p, c5p))
  // val draw = new Draw()
  // draw.saveToFile(group, "apollonius1.svg")
}
