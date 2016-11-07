package com.hamlazot.domain.scripts.users

import java.util.UUID
import com.hamlazot.domain.common.users.{UsersProtocol, UsersService}
import com.hamlazot.domain.scripts.M1
import com.hamlazot.domain.scripts.users.ScriptUsersAggregate
import com.hamlazot.domain.scripts.users.ScriptUsersModel.AUser

import scala.collection.mutable

/**
 * @author yoav @since 10/30/16.
 */
object ScriptUsersService extends UsersService with UsersProtocol with ScriptUsersAggregate {

//  object ScriptUsersProtocol extends UsersProtocol with ScriptUsersAggregate] {
//    override val aggregate: ScriptUsersAggregate = ScriptUsersAggregate
//  }
//  override val protocol: UsersProtocol[ScriptUsersAggregate] = ScriptUsersProtocol
//  override val aggregate: ScriptUsersAggregate = ScriptUsersAggregate
  override type Operation[A, B] = A => M1[B]

  private var users = mutable.MutableList.empty[User]

  override def createUser: (CreateUserRequest) => M1[CreateUserResponse] = {request =>
    val id = UUID.randomUUID()
    users += AUser(id, request.name, request.trustees)
    M1(CreateUserResponse(id))
  }

  override def getUser: (GetUserRequest) => M1[GetUserResponse] = {request =>
    M1(GetUserResponse(users.find(u => u.userId == request.userId).get))
  }

  override def deleteUser: (DeleteUserRequest) => M1[DeleteUserResponse] = {request =>
    val before = users.size
    users = users.filterNot(u => u.userId == request.userId)

    M1(DeleteUserResponse(before > users.size))
  }

  override def addTrustees: (AddTrusteesRequest) => M1[Boolean] = {request =>
    var transformed = false
    users.transform { u =>
    if (u.userId == request.userId) {
      transformed = true
      u.copy(trustees = request.trustees)
    }
    else {
      u
    }
  }
    M1(true)
  }

  override def addTrusters: (AddTrustersRequest) => M1[Boolean] = {request =>
    var transformed = false
    users.transform { u =>
      if (u.userId == request.userId) {
        transformed = true
        u.copy(trusters = request.trusters)
      }
      else {
        u
      }
    }
    M1(transformed)
  }

  override def removeTrustees: (RemoveTrusteesRequest) => M1[Boolean] = ???

  override def removeTrusters: (RemoveTrusteesRequest) => M1[Boolean] = ???
}
