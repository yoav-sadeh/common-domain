package com.hamlazot.domain.common.users

/**
 * @author yoav @since 11/2/16.
 */
trait UsersProtocol extends UsersAggregate {
  
  case class CreateUserRequest(name: String, trustees: Trustees)
  case class CreateUserResponse(userId: UserId)
  case class GetUserRequest(userId: UserId)
  case class GetUserResponse(user: User)
  case class DeleteUserRequest(userId: UserId)
  case class DeleteUserResponse(success: Boolean)
  case class AddTrusteesRequest(userId: UserId, trustees: Trustees)
  case class AddTrusteesResponse(success: Boolean)
  case class AddTrustersRequest(userId: UserId, trusters: Trusters)
  case class AddTrustersResponse(success: Boolean)
  case class RemoveTrusteesRequest(userId: UserId, trustees: Trustees)
  case class RemoveTrusteesResponse(success: Boolean)
  case class RemoveTrustersRequest(userId: UserId, trusters: Trusters)
  case class RemoveTrustersResponse(success: Boolean)

  case class UserDoesNotExistException(user: User) extends Exception(s"user $user does not exist")
}
