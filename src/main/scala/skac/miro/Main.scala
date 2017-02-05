package skac.miro

import graphics._
import Graphic._
import draw.svg._
import attribs._
import colors._
import examples._
import skac.miro.segments._

object Main {
   def main(args: Array[String]): Unit = {     
     val group = SimpleShapes2.group
     val draw = new Draw()
     draw.saveToFile(group, "simple_shapes_2.svg")
   }
}
