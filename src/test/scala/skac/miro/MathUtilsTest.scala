package skac.miro
import org.scalatest._
import skac.miro.MathUtils._

class MathUtilsTest extends FlatSpec with Matchers {
  "MathUtils" should "exactly calculate integral for x^3" in {
    integrate((x: Double) => {x * x * x}, 0, 10) should be (2500.0)
  }

  it should "approximately calculate integral for x^4" in {
    integrate((x: Double) => {x * x * x * x}, 0, 10) should be (10000.0)
  }

  // it should "has 1000 nodes after adding 1000 nodes" in {
  //   val g = new skac.euler.impl.fastindex.immutable.Graph[Any, Any]
  //   var g1000 = (1 to 1000).foldLeft(g){_ + _.i}
  //   g1000.nodeCount should be (1000)
  // }
  //
  // it should "convert properly NodeInfo to NodeDesignator" in {
  //   val g = new skac.euler.impl.fastindex.immutable.Graph[Any, Any]
  //   var g1 = g + "one".da
  //   val ni = g1.node(0.i).get
  //   val edges = g1.edgesOfNode(ni)
  //   println(edges)
  // }
}
