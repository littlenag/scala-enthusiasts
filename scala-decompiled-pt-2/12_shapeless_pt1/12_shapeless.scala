// scalac -classpath $HOME/.m2/repository/com/chuusai/shapeless_2.11/2.3.1/shapeless_2.11-2.3.1.jar -language:implicitConversions -language:postfixOps *.scala
import scala.language.implicitConversions
import scala.language.postfixOps

import shapeless._
import poly._
import syntax.singleton._

object Main {
  val someHList = "Dallas" :: (3,0.14159) :: 2 :: HNil

  object polyFunc extends Poly1 {
    implicit def atInt = at[Int](i => i*i)
    implicit def atString = at[String](s => s.toUpperCase)
    implicit def default[T] = at[T](i => i)
  }

  val mappedHList = someHList map polyFunc
}
