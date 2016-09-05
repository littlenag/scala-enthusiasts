def map_for_loop[A,B](l : List[A])(f : A => B) : List[B] = {
  var nl : List[B] = Nil

  for (el <- l) {
    nl = nl :+ f(el)
  }

  nl
}

def map_recursive_match[A,B](l : List[A])(f : A => B) : List[B] = l match {
  case h :: t => f(h) :: map_recursive_match(t)(f)
  case Nil => Nil
}

def map_recursive_if[A,B](l : List[A])(f : A => B) : List[B] = {
  if (l.isEmpty)
    Nil
  else
    f(l.head) :: map_recursive_if(l.tail)(f)
}

def map_tailrec[A,B](l : List[A])(f : A => B) : List[B] = {
  @scala.annotation.tailrec
  def helper(done:List[B], todo:List[A]) : List[B] = {
    if (todo.isEmpty)
      done
    else
      helper(done :+ f(todo.head), todo.tail)
  }
  helper(Nil, l)
}

def map_for_comprehension[A,B](l : List[A])(f : A => B) =
  for (a <- l) yield f(a)

def map_method_explicit[A,B](l : List[A])(f : A => B) =
  l.map(a => f(a))

def map_method_concise[A,B](l : List[A])(f : A => B) =
  l.map(f)

// More with for

def toUpper(maybeStrings:Option[String]*) = {
  for {
    maybeString <- maybeStrings
    s <- maybeString
  } yield s.toUpperCase()
}

for {
  x <- List(1, 2, 3)
  y <- List(7, 8, 9)
  z = x + y
  if z % 3 == 0
} yield z

//effectively same as do-notation in haskell
//http://underscore.io/blog/posts/2015/04/14/free-monads-are-simple.html
//
