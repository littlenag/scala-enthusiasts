// scalac -language:implicitConversions -language:postfixOps *.scala
import scala.language.implicitConversions
import scala.language.postfixOps

object Main {

  implicit class MyInt(i:Int) {
    def squared = i*i
  }

  implicit val implicitInt = 2

  implicit def intPair2int(p:(Int,Int)): Int = p._1 + p._2

  def mult(a:Int,b:Int)(implicit scalingFactor:Int) = a * b * scalingFactor

  def implicitParam = mult(3,3)

  def extensionMethod = 2 squared

  def typeConversion = {
    val p = (1,2)
    mult(p, 2)
  }
}
