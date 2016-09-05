// scalac -classpath $HOME/.m2/repository/com/chuusai/shapeless_2.11/2.3.1/shapeless_2.11-2.3.1.jar -language:implicitConversions -language:postfixOps *.scala
import scala.language.implicitConversions
import scala.language.postfixOps

import shapeless._
import poly._
import syntax.singleton._
import record._

object Main {
  case class Person(name:String, yearsCoding:Int, scalaEnthusiast:Boolean)

  val mark = Person("Mark", 10, true)
  val personGen = LabelledGeneric[Person]
  val markRecord = personGen.to(mark)

  val jimRecord =
    ('name            ->> "Jim") ::
    ('yearsCoding     ->> 20) ::
    ('scalaEnthusiast ->> true) ::
    HNil

  val jim = personGen.from(jimRecord)
}
