trait Plain {

  def print:String

}

class PlainImpl extends Plain {

  override def print = "PlainImpl"

}

trait Complex {

  def print:String

  val len : String => Int = _.length + r.nextInt

  private val r = scala.util.Random

  def someString = "a" * r.nextInt

}

class ComplexImpl extends Complex {

  def print = len(someString).toString

}