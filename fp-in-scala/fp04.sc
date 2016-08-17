sealed trait Exp

case class Num(n: Int) extends Exp
case class Plus(n1: Exp, n2: Exp) extends Exp
case class Mul(n1: Exp, n2: Exp) extends Exp
case class Sub(n1: Exp, n2: Exp) extends Exp
case class Div(n1: Exp, n2: Exp) extends Exp

def evaluate(e: Exp): Int = {
  e match {
    case Num(n) => n
    case Plus(l, r) => evaluate(l) + evaluate(r)
    case Mul(l, r) => evaluate(l) *  evaluate(r)
    case Sub(l, r) => evaluate(l) - evaluate(r)
    case Div(l, r) => evaluate(l) / evaluate(r)
  }
}

val timesTwo : Exp => Exp =
  Mul(Num(2),_)

implicit class ExpExt(e:Exp) {
  def squared : Exp = Mul(e,e)
}

//import fp04._
//val e = Plus(Mul(Num(3),Num(4)),Sub(Num(3),Num(4)))
//evaluate(e)
//evaluate(timesTwo(e))
//evaluate(e squared)

//interprettor pattern?
//just Int?
//Free Monad?
