trait Printer {
  def print:String
}

trait ConstantPrinter extends Printer {
  override def print = "constant"
}
