trait T1 {
  def foobar() = 1
}

class B extends T1 {
  override def foobar() = 42
}
