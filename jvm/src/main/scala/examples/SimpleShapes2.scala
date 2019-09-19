package com.github.skac112.miro.examples

import com.github.skac112.miro.graphics._
import com.github.skac112.miro.Graphic._
import com.github.skac112.miro.draw.svg._
import com.github.skac112.miro.attribs._
import com.github.skac112.miro.attribs.colors._
import com.github.skac112.miro._
import com.github.skac112.miro.graphics.compounds._
import scala.math._

object SimpleShapes2 {
  def group = {
    val ga = GenericAttribs(Some(MiroColor.red()), None)
    val stripe = Stripe(10, 20, 0, .5 * Pi, ga)
    Group(stripe :: Nil)
  }
}
