package com.github.skac112.miro.examples

import com.github.skac112.miro.graphics._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.draw.svg._
import com.github.skac112.miro.attribs._
import com.github.skac112.miro.attribs.colors._
import com.github.skac112.miro._
import com.github.skac112.miro.graphics.compounds._
import scala.math._

/**
 * Example - leaf.
 */
object ExampleLeaf {
  def group = {
    val ga = GenericAttribs(Some(MiroColor.red()), None)
    val leaf = Leaf((100.0, .0), 70.0, -10.0, ga)
    Group(leaf :: Nil)
  }
}
