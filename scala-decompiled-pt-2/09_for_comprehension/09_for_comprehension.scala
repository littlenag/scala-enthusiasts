object Main {
  def strLengths(l1:List[String]) = {
    for (a1 <- l1) yield a1.length
  }

  def filterNonEmptyByLengths(left:Option[String], right:Option[String]) = {
    for {
      l <- left
      r <- right
    } yield l + r
  }
}
