def head[T](l:Seq[T]) = l.head
def fst[A,B](t:(A,B)) = t._1

// Run in the Scala REPL
// scala> :type head _
// scala> :type fst _

// How does this compare to Haskell?

// But what if we were to write: val fst[A,B] : (A,B) => A = (t) => t._1
// Really just a shorthand anonymous classes, e.g:
// new Function1[Int, Int] {
//  def apply(x: Int): Int = x + 1
// }
// http://docs.scala-lang.org/tutorials/tour/anonymous-function-syntax.html
