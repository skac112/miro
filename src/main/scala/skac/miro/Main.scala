package skac.miro

import graphics._
import Graphic._
import draw.svg._
import attribs._
import colors._
import examples._
import skac.miro.segments._

object Main extends App {
  val group = Example1.group
  val draw = new Draw()
  draw.saveToFile(group, "example1.svg")   
}
