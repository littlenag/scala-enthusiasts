class Printer {
  def print:String = s"Printing pages"
}

// Dotty trait params, can see how this would be useful
// since currently Scala won't let parents of traits have params
trait ConstantPrinter extends Printer {
  override def print = "constant"
}
