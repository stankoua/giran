package common

import java.util.concurrent.atomic.AtomicLong

trait Client {

  private val idGenerator = new AtomicLong()

  def nextId() : Long = idGenerator.getAndIncrement()

}
