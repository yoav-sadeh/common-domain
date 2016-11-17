package com.hamlazot.domain.tests.specs

import com.hamlazot.domain.tests.scripts.Scenarios
import org.specs2.mutable.Specification

/**
 * @author yoav @since 11/17/16.
 */
class UsersSpec extends Specification with Scenarios{

  val yoav = "Yoav"
  val maya = "Maya"
  val ido = "Ido"

  def genMail(name: String) = s"$name@gmail.com"

   "users service" should {
     "create user" in{
       val user = createAccountAndUser(yoav, genMail(yoav), Nil)
       user.value.user.name shouldEqual yoav
     }
   }
}
