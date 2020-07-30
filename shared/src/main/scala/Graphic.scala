package com.github.skac112.miro

import attribs._
import attribs.colors._
import graphics.Group._
import com.github.skac112.miro.align._
import com.github.skac112.vgutils.transform._
import com.github.skac112.vgutils._
import com.github.skac112.vgutils.transform._

object Graphic {
  case class GenericAttribs(
    fillO: Option[Fill] = None,
    strokeO: Option[Stroke] = None,
    /**
     * Przypisana transformacja afiniczna.
     */
    transformO: Option[Affine] = None) {
  }

  val defaultGenericAttribs = GenericAttribs()
}

/**
 * @author slawek
 */
trait Graphic {
  // type T = this.type
  import Graphic._
  import graphics._
  import graphics.compounds._

  def genericAttribs: GenericAttribs = defaultGenericAttribs

  def setGenericAttribs(newGenAttrs: GenericAttribs): Graphic

  // def same: T = this

  /**
   * PosGraphic dla tej grafiki
   */
//  def pg(pt: Point = ori) = (this, pt)

  def %(pt: Point = ori) = (this, pt)

  /**
    * Inclusion of other posgraphic. If this graphic is a group, the other posgraphic is added as a new element of a
    * group. If this graphic is not a group, a two-element group is created.
    * @param Other
    * @return
    */
  def +(other: PosGraphic[Graphic]): Graphic = (this, other) match {
    // same graphics
    case (g, (`empty`, _)) => g
    // wrapping posgraphic in a group
    case (`empty`, gp) => Group(Seq(gp))
    // including to a group
    case (Group(ensemble, ga), gp) => Group(ensemble :+ gp, ga)
    // creating 2-element group
    case (g, gp) => Group(Seq((g, ori), gp))
  }

  /**
   * Inkluzja z rozbiciem (obu grafik). Przesuniecie elementow 2-giej grafiki NIE DOTYCZY ostatniego elementu
   * 1-szej grafiki (jak przy zwyklej inkluzji).
   */
  def ++(Other: PosGraphic[Graphic]) = Group(ensemble(this) ++ (ensemble(Other._1) map {pos_g => (pos_g._1, pos_g._2 + Other._2)}))

  /**
   * Utworzenie grupy 2-elementowej
   */
//  def ++(Other: PosGraphic[Graphic]) = Group(Seq(pg(), Other))

  /**
   * Inkluzja transformacji ostatniego elementu
   */
  def +>(trans: MiroTransform) = this + trans(ensemble(this).last)

  /**
   * Dodaje do biezacej grafiki zlokalizowanej (PosGraphic) inna grafike zlokalizowana tworzac dwuelementowa grupe
   */
//  def put(Other: PosGraphic) = Group(Seq(pg, Other))

  def doTransform(trans: MiroTransform): Graphic = this
  def t(trans: MiroTransform) = doTransform(trans: MiroTransform)
//  def r(angle: Double) = doTransform(Rotation(angle))
//  def r(rot: Rotation) = doTransform(rot)
//  def s(scale: Scale) = doTransform(scale)

  def fill(fill: Fill) = setGenericAttribs(genericAttribs.copy(fillO = Some(fill)))

  def stroke(stroke: Stroke) = setGenericAttribs(genericAttribs.copy(strokeO = Some(stroke)))

  def strCol(col: MiroColor) = {
    val stroke = genericAttribs.strokeO match {
      case Some(stroke) => stroke
      case _ => Stroke(new MiroColor(.0, .0, .0), 1.0)
    }
    setGenericAttribs(genericAttribs.copy(strokeO = Some(stroke.copy(color = col))))
  }

  def strWidth(w: Double) = {
    val stroke = genericAttribs.strokeO match {
      case Some(stroke) => stroke
      case _ => Stroke(new MiroColor(.0, .0, .0), 1.0)
    }
    setGenericAttribs(genericAttribs.copy(strokeO = Some(stroke.copy(width = w))))
  }

  /**
   * Creates PosGraphic from this graphic
   * (ie. tuple of graphic and point) using given point.
   */
  def at(pt: Point) = (this, pt)

  // def bounds = Bounds.empty
  def bounds: Bounds

  def toC: Circle = this.asInstanceOf[Circle]
  def toStripe: Stripe = this.asInstanceOf[Stripe]
  def toAS: ArcSection = this.asInstanceOf[ArcSection]
  def toRing: Ring = this.asInstanceOf[Ring]
  def toE: Ellipse = this.asInstanceOf[Ellipse]
  def toG: Group = this.asInstanceOf[Group]
  def toL: Line = this.asInstanceOf[Line]
  def toP: Path = this.asInstanceOf[Path]
  def toPolyg: Polygon = this.asInstanceOf[Polygon]
  def toPolyl: Polyline = this.asInstanceOf[Polyline]
  def toR: Rect = this.asInstanceOf[Rect]
  def toS: Square = this.asInstanceOf[Square]
  def toT: Triangle = this.asInstanceOf[Triangle]
  def toQ: Quad = this.asInstanceOf[Quad]

  def characteristicPt(code: Symbol): Point = code match {
    case 'ORIG => Point(0, 0)
  }

  def align(g: Graphic) = Align(this, g)
  def alignTo(g: Graphic) = Align(g, this)
  def alignSelf = Align(this, this)
  def addAlign(g: Graphic) = AddAlign(this, g)

  /**
   * Determines if a given point lies on a graphic. Returns None by default, meaning that
   * it is unknown if a given point lies on a graphic.
   * @param pt
   * @return
   */
  def hitTest(pt: Point): Option[Boolean] = if (bounds.hitTest(pt)) {
    None
  }
  else Some(false)
}
