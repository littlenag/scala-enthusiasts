trait Printer {
  def print:String
}

object ConstantPrinter extends Printer {
  override def print = "constant"
}
