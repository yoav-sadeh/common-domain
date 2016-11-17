package com.hamlazot.domain.tests.specs

import com.hamlazot.domain.tests.scripts.Scenarios
import com.hamlazot.domain.tests.scripts.users.ScriptUsersModel.AUser
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

/**
 * @author yoav @since 11/17/16.
 */
class UsersSpec extends Specification with Mockito with Scenarios {

  val yoav = "Yoav"
  val maya = "Maya"
  val ido = "Ido"

  def genMail(name: String) = s"$name@gmail.com"

  "users service" should {
    "create user" in {
      val user = createAccountAndUser(yoav, genMail(yoav), Nil)
      user.value.user.name shouldEqual yoav
    }
    step("stop here for a second")
    "create user with trustees" in {
      val result = for {
        trustedUserResponse <- createAccountAndUser(ido, genMail(ido), Nil)
        user <- createAccountAndUserWithTrustees(maya, genMail(maya), trustedUserResponse.user)
      } yield (user, trustedUserResponse)

      result.value._1.user.name shouldEqual maya
      result.value._1.user.trustees.contains(result.value._2.user)
    }

    "create user with trustees" in {
      import com.hamlazot.domain.tests.scripts.users.ScriptUsersService.UserDoesNotExistException
      val trustedUser = mock[AUser]
      createAccountAndUserWithTrustees(maya, genMail(maya), trustedUser) should throwA[UserDoesNotExistException]
    }
  }


}

class StepSpec extends Specification {
  override def is = s2"""
  this is example 1 $ok
  this is example 2 $ok
  ${step("stop here for a second")}

  this is example 3 $ok
  this is example 4 $ok
"""
}
