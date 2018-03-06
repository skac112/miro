package skac.miro

import org.scalatest._
import skac.miro.transform._

class AffineTest extends FlatSpec with Matchers {
  "Mirror transform" should "adequately transform points" in {
    val p1 = Point(-3, 1)
    val p2 = Point(0, 4)
    val m1 = Affine.mirror(3, -1, 0)
    val pm11 = m1 transPt p1
    pm11.closeTo(Point(3, -1)) should be (true)
    println(pm11)
    // point on a given line - coordinates should be saved
    val pm12 = m1 transPt Point(-5, -5)
    println(pm12)
    pm12.closeTo(Point(1, -7)) should be (true)
    val m2 = Affine.mirror(1, 0, -3)
    val pm21 = m2 transPt p1
    println(pm21)
    pm21.closeTo(Point(9, 1)) should be (true)
    val pm22 = m2 transPt p2
    println(pm22)
    pm22.closeTo(Point(6, 4)) should be (true)
    val m3 = Affine.mirror(0, 1, -2)
    val pm31 = m3 transPt p1
    println(pm31)
    pm31.closeTo(Point(-3, 3)) should be (true)
    val pm32 = m3 transPt p2
    println(pm32)
    pm32.closeTo(Point(0, 0)) should be (true)
  }
}

// class GraphTest extends AssertionsForJUnit {
//
//   @Test
//   def createGraph() {
//     val g = new Graph[Any, Any]
//   }
//
//   @Test
//   def addNodes() {
//     val g = new Graph[Any, Any]
//     for (i <- 1 to 10000)
//       g.addNode(i)
//     assertEquals(g.nodeCount, 10000)
//   }
//
//   @Test
//   def addEdges() {
//     val g = new Graph[Any, Any]
//     for (i <- 1 to 10000)
//       g.addNode(i)
//     assertEquals(g.nodeCount, 10000)
//     for (i <- 1 to 1000)
//       g.addEdge(i, g.NodeIdxDesignator(i), g.NodeIdxDesignator(i + 1))
//     assertEquals(g.edgeCount, 1000)
//   }
// }
