package com.github.skac112.miro.graphics

import com.github.skac112.miro.Graphic._
import com.github.skac112.miro._
import com.github.skac112.vgutils._

object Group {
  lazy val empty = Group(Nil)
}

/**
 * @author slawek
 */
case class Group(elements: Ensemble, override val genericAttribs: GenericAttribs = defaultGenericAttribs) extends Graphic {

  /**
   * Tworzy nowa grupe przez dodanie do biezacej grupy elementu powstalego przez transformacje ostatniego elementu
   * tej grupy (z uwzglednieniem przesuniecia).
   */
  def onLast(trans: MiroTransform) = {
    val new_pos_graph = trans(elements.last)
    Group(elements :+ new_pos_graph, genericAttribs)
  }

  def setGenericAttribs(newGenAttrs: GenericAttribs) = copy(genericAttribs = newGenAttrs)

  override def bounds = elements.foldLeft(Bounds.empty) {_ + com.github.skac112.miro.bounds(_)}
}
