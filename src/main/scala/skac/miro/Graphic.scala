package skac.miro

import skac.miro.transform._
import attribs._
import attribs.colors._

object Graphic {
  case class GenericAttribs(
    fillO: Option[Fill] = None,
    strokeO: Option[Stroke] = None,
    /**
     * Przypisana transformacja afiniczna - ukłon w stronę SVG.
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
  def pg = (this, ori)

  /**
   * Inkluzja. 2-ga grafika dodawana jest bez rozbijania. Jesli 1-sza grafika jest grupa, to nastepuje
   * poszerzenie grupy o dodawana grafike. Jesli 1-sza grafika nie jest grupa, to tworzona jest 2-elementowa
   * grupa. Przesuniecie 2-giej grafiki dotyczy ostatniego elementu w 1-szej grafice. Dzialanie to jest nieprzemienne
   * ale laczne
   */
  def *+(Other: PosGraphic) = Group(ensemble(this) :+ (Other._1, Other._2 + ensemble(this).last._2))

  /**
   * Inkluzja z rozbiciem (obu grafik). Przesuniecie elementow 2-giej grafiki NIE DOTYCZY ostatniego elementu
   * 1-szej grafiki (jak przy zwyklej inkluzji).
   */
  def **(Other: PosGraphic) = Group(ensemble(this) ++ (ensemble(Other._1) map {pos_g => (pos_g._1, pos_g._2 + Other._2)}))

  /**
   * Utworzenie grupy 2-elementowej
   */
  def ++(Other: PosGraphic) = Group(Seq(pg, Other))

  /**
   * Inkluzja transformacji ostatniego elementu
   */
  def *>(trans: Transform) = this *+ trans((ensemble(this).last._1, ori))

  /**
   * Dodaje do biezacej grafiki zlokalizowanej (PosGraphic) inna grafike zlokalizowana tworzac dwuelementowa grupe
   */
//  def put(Other: PosGraphic) = Group(Seq(pg, Other))

  /**
   * Dodaje elementy biezacej grafiki z elementami innej grafiki tworzac grupe
   */
//  def join(Other: PosGraphic) = Group(ensemble(this) ++ (ensemble(Other._1) map {pos_g => (pos_g._1, pos_g._2 + Other._2)}))

  /**
   * Tworzy grupe poprzez dodanie do biezacej grafiki tej samej grafiki poddanej transformacji
   */
//  def putTrans(trans: Transform) = Group(Seq(pg, trans(this)))

  /**
   * Tworzy grupe poprzez dodanie do elementow biezacej grafiki elementow tej samej grafiki poddanej transformacji.
   */
//  def joinTrans(trans: Transform) = {
//    val this_ens = ensemble(this)
//    val trans_out = trans(this)
//    val trans_ens = ensemble(trans_out._1)
//    val trans_move = trans_out._2
//    Group(this_ens ++ (trans_ens) map {pos_g => (pos_g._1, pos_g._2 + trans_move)} )
//  }

  def doTransform(trans: Transform): Graphic = this
  def t(trans: Transform) = doTransform(trans: Transform)
  def r(angle: Double) = doTransform(Rotation(angle))
  def r(rot: Rotation) = doTransform(rot)
  def s(scale: Scale) = doTransform(scale)

  def fill(fill: Fill) = setGenericAttribs(genericAttribs.copy(fillO = Some(fill)))

  def stroke(stroke: Stroke) = setGenericAttribs(genericAttribs.copy(strokeO = Some(stroke)))

  def strCol(col: Color) = {
    val stroke = genericAttribs.strokeO match {
      case Some(stroke) => stroke
      case _ => Stroke(Color(.0, .0, .0), 1.0)
    }
    setGenericAttribs(genericAttribs.copy(strokeO = Some(stroke.copy(color = col))))
  }

  def strWidth(w: Double) = {
    val stroke = genericAttribs.strokeO match {
      case Some(stroke) => stroke
      case _ => Stroke(Color(.0, .0, .0), 1.0)
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
}
