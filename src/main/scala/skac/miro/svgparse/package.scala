package skac.miro

import scala.xml._
import Dsl._
import graphics._
import segments._
import scala.collection.immutable._
import com.github.skac112.vgutils._

package object svgparse {
  type SegPair = (Option[Segment], Point)
  type SegPairSeq = Seq[SegPair]
  def numPat = new StringOps("(?:\\-)?(?:\\d*\\.)?\\d+").r

  private def doubleAttr(elem: Elem, attrName: String) = (elem \ s"@$attrName").text.toDouble

  private def parseRotate(rotStr: String): Double = {
    ???
  }

  private def parsePathDAttr(d: String): (Subpaths, Point) = {
    val start_pt: Point = findFirstPt(d)
    // pattern for splitting d attr into subpaths
    val subpath_p = new StringOps("(?i)([m][^m]+)").r
    // pattern for splitting subpath into segments
    val seg_p = new StringOps("(?i)([mlhvcsqta][^mlhvcsqta]+)").r
    var pt = start_pt
    val subpaths = subpath_p.findAllIn(d).toList map {subpath_str =>
      // for each subpath, current point starts with overall start_pt, in
      // contrast with svg approach where subpath current point starts with
      // end current point of preceding subpath
      val segs_pts: SegPairSeq = seg_p.findAllIn(subpath_str).toList flatMap {seg_str =>
        val seg_seq = parseSegSeq(seg_str, pt)
        // new current point
        pt = seg_seq.last._2
        seg_seq
      }
      val curve = new GenericSegCurve {
        // filtering out 1-st element because it corresponds to "moveTo" part (m or M)
        override lazy val segments = segs_pts.tail map {_._1.get}
      }
      // location point of subpath is relative to start_pt point
      val loc_pt = segs_pts(0)._2 - start_pt
      (curve, loc_pt)
    }
    (subpaths, start_pt)
  }

  private def findFirstPt(d: String): Point = {
    val num_pair = numPat.findAllIn(d.tail).take(2).map {_.toDouble}
    Point(num_pair.next, num_pair.next)
  }

  /**
   * Parses sequence of segments of the same type. Returns pairs where:
   * - first element is Option with possible segment. It is None for m and M parts.
   * - second element is current point (absolute coordinates)
   * For m and M parts returns pair with "empty" segment - i.e. 1-st element is None.
   */
  private def parseSegSeq(segStr: String, pt: Point): SegPairSeq = {
    val num_str = segStr.tail
    val nums = numPat.findAllIn(num_str).toList map {_.toDouble}
    lazy val pts = nums.grouped(2).toList map {pair => Point(pair(0), pair(1))}
    segStr.head match {
      case 'm' => {
        var cur_pt = pt + pts(0)
        val move_to = (None, cur_pt)
        val line_segs = pts.tail map {pt =>
          val seg = LineSeg(pt)
          cur_pt = cur_pt + pt
          (Some(seg), cur_pt)
        }
        move_to :: line_segs
      }

      case 'M' => {
        var cur_pt = pts(0)
        val move_to = (None, cur_pt)
        val line_segs = pts.tail map {pt =>
          val seg = LineSeg(pt - cur_pt)
          cur_pt = pt
          (Some(seg), cur_pt)
        }
        move_to :: line_segs
      }

      // relative quadratic
      case 'q' => {
        var cur_pt = pt
        pts.grouped(2).toList map {pt_pair =>
          val (cnt_pt, end_pt) = (pt_pair(0), pt_pair(1))
          val seg = Quadratic(cnt_pt, end_pt)
          cur_pt = cur_pt + end_pt
          (Some(seg), cur_pt)
        }
      }

      // absolute quadratic
      case 'Q' => {
        var cur_pt = pt
        pts.grouped(2).toList map {pt_pair =>
          val (cnt_pt, end_pt) = (pt_pair(0), pt_pair(1))
          val seg = Quadratic(cnt_pt - cur_pt, end_pt - cur_pt)
          cur_pt = end_pt
          (Some(seg), cur_pt)
        }
      }

      // relative smooth quadratic
      case 't' => {
        var cur_pt = pt
        pts.toList map {end_pt =>
          val seg = SmoothQuadratic(end_pt)
          cur_pt = cur_pt + end_pt
          (Some(seg), cur_pt)
        }
      }

      // absolute smooth quadratic
      case 'T' => {
        var cur_pt = pt
        pts.toList map {end_pt =>
          val seg = SmoothQuadratic(end_pt - cur_pt)
          cur_pt = end_pt
          (Some(seg), cur_pt)
        }
      }

      // relative cubic
      case 'c' => {
        var cur_pt = pt
        pts.grouped(3).toList map {pt_triple =>
          val (cp1, cp2, end_pt) = (pt_triple(0), pt_triple(1), pt_triple(2))
          val seg = Cubic(cp1, cp2, end_pt)
          cur_pt = cur_pt + end_pt
          (Some(seg), cur_pt)
        }
      }

      case 'C' => {
        // absolute cubic
        var cur_pt = pt
        pts.grouped(3).toList map {pt_triple =>
          val (cp1, cp2, end_pt) = (pt_triple(0), pt_triple(1), pt_triple(2))
          val seg = Cubic(cp1 - cur_pt, cp2 - cur_pt, end_pt - cur_pt)
          cur_pt = end_pt
          (Some(seg), cur_pt)
        }
      }

      // relative smooth cubic
      case 's' => {
        var cur_pt = pt
        pts.grouped(3).toList map {pt_pair =>
          val (cp2, end_pt) = (pt_pair(0), pt_pair(1))
          val seg = SmoothCubic(cp2, end_pt)
          cur_pt = cur_pt + end_pt
          (Some(seg), cur_pt)
        }
      }

      // absolute smooth cubic
      case 'S' => {
        var cur_pt = pt
        pts.grouped(2).toList map {pt_pair =>
          val (cp2, end_pt) = (pt_pair(0), pt_pair(1))
          val seg = SmoothCubic(cp2 - cur_pt, end_pt - cur_pt)
          cur_pt = end_pt
          (Some(seg), cur_pt)
        }
      }

      // relative arc
      case 'a' => {
        var cur_pt = pt
        nums.grouped(7).toList map {num_7 =>
          val laf = num_7(3) == '1'
          val sf = num_7(4) == '1'
          val end_pt = Point(num_7(5), num_7(6))
          cur_pt = cur_pt + end_pt
          val seg = Arc(num_7(0), num_7(1), Angle.fromDeg(num_7(2)), laf, sf, end_pt)
          (Some(seg), cur_pt)
        }
      }

      // absolute arc
      case 'A' => {
        var cur_pt = pt
        nums.grouped(7).toList map {num_7 =>
          val laf = num_7(3) == '1'
          val sf = num_7(4) == '1'
          val end_pt = Point(num_7(5), num_7(6))
          val seg = Arc(num_7(0), num_7(1), Angle.fromDeg(num_7(2)), laf, sf, end_pt - cur_pt)
          cur_pt = end_pt
          (Some(seg), cur_pt)
        }
      }

      // relative line
      case 'l' => {
        var cur_pt = pt
        pts map {end_pt =>
          val seg = LineSeg(end_pt)
          cur_pt = cur_pt + end_pt
          (Some(seg), cur_pt)
        }
      }

      // absolute LineSeg
      case 'L' => {
        var cur_pt = pt
        pts map {end_pt =>
          val seg = LineSeg(end_pt - cur_pt)
          cur_pt = end_pt
          (Some(seg), cur_pt)
        }
      }

      // relative horizontal
      case 'h' => {
        var cur_pt = pt
        nums map {num =>
          cur_pt = cur_pt + Point(num, 0.0)
          val seg = HSeg(num)
          (Some(seg), cur_pt)
        }
      }

      // absolute horizontal
      case 'H' => {
        var cur_pt = pt
        nums map {num =>
          val seg = HSeg(num - cur_pt.x)
          cur_pt = Point(num, 0.0)
          (Some(seg), cur_pt)
        }
      }

      // relative vertical
      case 'v' => {
        var cur_pt = pt
        nums map {num =>
          cur_pt = cur_pt + Point(0.0, num)
          val seg = VSeg(num)
          (Some(seg), cur_pt)
        }
      }

      // absolute vertical
      case 'V' => {
        var cur_pt = pt
        nums map {num =>
          val seg = VSeg(num - cur_pt.y)
          cur_pt = Point(0.0, num)
          (Some(seg), cur_pt)
        }
      }

      case l @ _ if (l == 'z' || l == 'Z') => List((Some(Close()), pt))
      case _ => throw new Exception("Nieznany typ segmentu")
    }
  }

  def fromElem(elem: Elem): PosGraphic[Graphic] = {
    elem.label match {
      case "circle" => (C(doubleAttr(elem, "r")), Point(doubleAttr(elem, "cx"), doubleAttr(elem, "cy")))
      case "ellipse" => (E(doubleAttr(elem, "rx"), doubleAttr(elem, "ry")), Point(doubleAttr(elem, "cx"), doubleAttr(elem, "cy")))
      case "path" => {
        val (subpaths, start_pt) = parsePathDAttr((elem \ "@d").text)
        Path(subpaths) at start_pt
      }
      // case "line" => {
      //   val p =
      // }
    }
  }
}
