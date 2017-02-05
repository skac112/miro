package skac.miro

import skac.miro.Graphic._

/**
 * @author slawek
 */
case class Group(elements: Ensemble, override val genericAttribs: GenericAttribs = defaultGenericAttribs) extends Graphic {

  /**
   * Tworzy nowa grupe przez dodanie do biezacej grupy elementu powstalego przez transformacje ostatniego elementu
   * tej grupy (z uwzglednieniem przesuniecia).
   */
  def onLast(trans: Transform) = {
    val new_pos_graph = trans(elements.last)
    Group(elements :+ new_pos_graph, genericAttribs)
  }
}
