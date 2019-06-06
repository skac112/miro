package skac.miro.attribs.colors

import skac.miro.attribs._

import scala.math._
import skac.miro._
import com.github.skac112.vgutils.{Angle, Color, _}
import com.github.skac112.vgutils.Color._

// import javafx.scene.paint.{Color => FXColor}

object MiroColor {
  def unapply(color: MiroColor) = Color.unapply(color)
  implicit def toMiroColor(color: Color) = new MiroColor(color.r, color.g, color.b, color.a)
  def red(v: Double = 1.0): MiroColor = Color.red(v)
  def green(v: Double = 1.0): MiroColor = Color.green(v)
  def blue(v: Double = 1.0): MiroColor = Color.blue(v)
  def cyan(v: Double = 1.0): MiroColor = Color.cyan(v)
  def magenta(v: Double = 1.0): MiroColor = Color.magenta(v)
  def yellow(v: Double = 1.0): MiroColor = Color.yellow(v)
  lazy val white: MiroColor = Color.white
  lazy val black: MiroColor = Color.black
  val transparent: MiroColor = Color.transparent
}

//object Color {
//  def red(v: Double = 1.0) = new Color(v, .0, .0)
//  def green(v: Double = 1.0) = new Color(.0, v, .0)
//  def blue(v: Double = 1.0) = new Color(.0, .0, v)
//  def cyan(v: Double = 1.0) = new Color(.0, v, v)
//  def magenta(v: Double = 1.0) = new Color(v, .0, v)
//  def yellow(v: Double = 1.0) = new Color(v, v, .0)
//
//  def normalize(v: Double) = v match {
//    case v if v > 1.0 => 1.0
//    case v if v < 0.0 => 0.0
//    case v => v
//  }
//
//  lazy val pi_3_inv = 3.0 / Pi
//
//  def hsl(hue: Angle, sat: Double, l: Double) = {
//    // chroma
//    val C = (1 - abs(2*l - 1.0))*sat
//    val hue_p = hue.value * pi_3_inv
//    val X = C*(1 - abs(hue_p % 2.0 - 1.0))
//    val (r1, g1, b1) = hue_p match {
//      case _ if hue_p >= 0 && hue_p < 1 => (C, X, .0)
//      case _ if hue_p >= 1 && hue_p < 2 => (X, C, .0)
//      case _ if hue_p >= 2 && hue_p < 3 => (.0, C, X)
//      case _ if hue_p >= 3 && hue_p < 4 => (.0, X, C)
//      case _ if hue_p >= 4 && hue_p < 5 => (X, .0, C)
//      case _  => (C, .0, X)
//      // case _ if hue_p >= 5 => (C, .0, X)
//    }
//    val m = l - 0.5 * C
//    Color(r1 + m, g1 + m, b1 + m)
//  }
//
//  val transparent = Color(.0, .0, .0, .0)
//
//  def hex2int(hex: String): Int = Integer.parseInt(hex, 16)
//  def hex2double(hex: String) = hex2int(hex) / 255.0
//}

class MiroColor(r: Double, g: Double, b: Double, a: Double = 1.0)
  extends com.github.skac112.vgutils.Color(r, g, b, a) with Fill {
  import Color._

  def this(webStr: String) = this(hex2double(webStr.substring(0, 2)),
    hex2double(webStr.substring(2, 4)),
    hex2double(webStr.substring(4, 6)))

  override def +:(other: Color): MiroColor = super.+:(other)
  override def -:(other: Color): MiroColor = super.-:(other)
  override def addR(v: Double = 1.0): MiroColor = super.addR(v)
  override def addG(v: Double = 1.0): MiroColor = super.addG(v)
  override def addB(v: Double = 1.0): MiroColor = super.addB(v)
  override def addH(v: Angle): MiroColor = super.addH(v)
  override def addS(v: Double): MiroColor = super.addS(v)
  override def addL(v: Double): MiroColor = super.addL(v)
  override def setH(new_h: Double): MiroColor = super.setH(new_h)
  override def setS(new_s: Double): MiroColor = super.setS(new_s)
  override def setL(new_l: Double): MiroColor = super.setL(new_l)
  override def setA(new_a: Double): MiroColor = super.setA(new_a)

//  def +:(other: MiroColor) = MiroColor(normalize(r + other.r), normalize(g + other.g), normalize(b + other.b), a)
//  def -:(other: MiroColor) = MiroColor(normalize(r - other.r), normalize(g - other.g), normalize(b - other.b), a)
//
//  override lazy val toString = {
//    // konwersja wartosci Double w zakresie <0; 1> na dwucyfrowy lancuch heksadecymalny
//    val to_hex = (value: Double) => f"${round(value*255)}%02x"
//    "#" + (Seq(r, g, b) map to_hex reduceLeft {_ + _})
//  }
//
//  private lazy val M =  List(r, g, b).max
//
//  private lazy val m = List(r, g, b).min
//
//  /**
//   * Chroma
//   */
//  private lazy val C = M - m
//
//  /**
//   * Wartosc hue w modelu HSL. Zrodlo:
//   * https://en.wikipedia.org/wiki/HSL_and_HSV
//   */
//  lazy val h: Angle = {
//    val hue_60 = (r, g, b, C) match {
//      case (_, _, _, .0) => .0
//      case _ if M == r => (g - b) / C % 6
//      case _ if M == g => (b - r) / C + 2
//      case _ => (r - g) / C + 4
//    }
//    Angle(Angle.normVal(hue_60 * Pi / 3.0))
//  }
//
//  /**
//   * Wartosc lightness (L) w modelu HSL. Zakres: <0; 1>. Zrodlo:
//   * https://en.wikipedia.org/wiki/HSL_and_HSV
//   */
//  lazy val l = 0.5 * (M + m)
//
//  /**
//   * Nasycenie (S) w modelu HSL. Zakres: <0; 1>. Zrodlo:
//   * https://en.wikipedia.org/wiki/HSL_and_HSV
//   */
//  lazy val s: Double = l match {
//    case 1.0 => 0
//    case 0 => 1.0
//    case _ => C / (1.0 - abs(2*l - 1.0))
//  }
//
//  def addR(v: Double = 1.0) = MiroColor(normalize(r + v), g, b)
//  def addG(v: Double = 1.0) = MiroColor(r, normalize(g + v), b)
//  def addB(v: Double = 1.0) = MiroColor(r, g, normalize(b + v))
//  def addH(v: Angle) = hsl(h + v, s, l)
//  def addS(v: Double) = hsl(h, normalize(s + v), l)
//  def addL(v: Double) = hsl(h, s, normalize(l + v))
}
