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
trait Graphic[+D] {
  def metadataO: Option[D] = None
  import Graphic._
  import graphics._
  import graphics.compounds._

  def genericAttribs: GenericAttribs = defaultGenericAttribs

  def setGenericAttribs(newGenAttrs: GenericAttribs): Graphic[D]

  // def same: T = this

  /**
   * PosGraphic dla tej grafiki
   */
//  def pg(pt: Point = ori) = (this, pt)

//  def %(pt: Point = ori) = (this, pt)

  /**
    * Inclusion of other posgraphic. If this graphic is a group, the other posgraphic is added as a new element of a
    * group. If this graphic is not a group, a two-element group is created.
    * @param Other
    * @return
    */
//  def +(other: PosGraphic[_, _]): Graphic[_] = (this, other) match {
//    // same graphics
//    case (g, (`empty`, _)) => g
//    // wrapping posgraphic in a group
//    case (`empty`, gp) => Group[D](Seq(gp))
//    // including to a group
//    case (Group(ensemble, ga, metadataO), gp) => Group(ensemble :+ gp, ga, metadataO)
//    // creating 2-element group
//    case (g, gp) => Group(Seq((g, ori), gp))
//  }

//  def joinMetadata(data1: Any, data2: Any) = (data1, data2) match {
//    case (Some(subst_d1), None) => subst_d1
//    case (None, Some(subst_d2)) => subst_d2
//    case (Some(d1: Seq[_]), Some(d2: Seq[_])) => Some(d1 ++ d2)
//    case (d1: Seq[_], d2) => d1 :+ d2
//    case (d1, d2: Seq[_]) => d1 +: d2
//    case _ => Seq(data1, data2)
//  }

  /**
   * Inkluzja z rozbiciem (obu grafik). Przesuniecie elementow 2-giej grafiki NIE DOTYCZY ostatniego elementu
   * 1-szej grafiki (jak przy zwyklej inkluzji).
   */
//  def ++[OG <: Graphic[OG, OD], OD](Other: PosGraphic[OG, OD]) = Group(ensemble(this) ++ (ensemble(Other._1) map {pos_g => (pos_g._1, pos_g._2 + Other._2)}))

  /**
   * Utworzenie grupy 2-elementowej
   */
//  def ++(Other: PosGraphic[Graphic]) = Group(Seq(pg(), Other))

  /**
   * Inkluzja transformacji ostatniego elementu
   */
//  def +>(trans: MiroTransform) = this + trans(ensemble(this).last)

  /**
   * Dodaje do biezacej grafiki zlokalizowanej (PosGraphic) inna grafike zlokalizowana tworzac dwuelementowa grupe
   */
//  def put(Other: PosGraphic) = Group(Seq(pg, Other))

  def doTransform(trans: MiroTransform): Graphic[D] = this
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
   * (i. e. tuple of graphic and point) using given point.
   */
  def at(pt: Point) = (this, pt)

  // def bounds = Bounds.empty
  def bounds: Bounds

  def toC = this.asInstanceOf[Circle[D]]
  def toStripe = this.asInstanceOf[Stripe[D]]
  def toAS = this.asInstanceOf[ArcSection[D]]
  def toRing = this.asInstanceOf[Ring[D]]
  def toE = this.asInstanceOf[Ellipse[D]]
  def toG = this.asInstanceOf[Group[D]]
  def toL = this.asInstanceOf[Line[D]]
  def toP = this.asInstanceOf[Path[D]]
  def toPolyg = this.asInstanceOf[Polygon[D]]
  def toPolyl = this.asInstanceOf[Polyline[D]]
  def toR = this.asInstanceOf[Rect[D]]
  def toS = this.asInstanceOf[Square[D]]
  def toT = this.asInstanceOf[Triangle[D]]
  def toQ = this.asInstanceOf[Quad[D]]

  def characteristicPt(code: Symbol): Point = code match {
    case 'ORIG => Point(0, 0)
  }

  def align[G <: Graphic[D], D](g: G) = Align[G, D](this, g)
  def alignTo(g: Graphic[_]) = Align[this.type, D](g, this)
  def alignSelf = Align[this.type, D](this, this)
//  def addAlign(g: Graphic[_]) = AddAlign(this, g)

  /**
   * Determines if a given point lies on a graphic. Returns None by default, meaning that
   * it is unknown if a given point lies on a graphic.
   * @param pt
   * @return
   */
  def hitTest(pt: Point): Option[Boolean] = if (bounds.hitTest(pt)) {
    None
  }
  else {
    Some(false)
  }
}
