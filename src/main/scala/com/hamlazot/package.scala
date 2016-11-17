package com

import scala.util.Try

/**
 * @author yoav @since 9/9/16.
 */
package object hamlazot {

  trait CommonOperations {
    type Operation[A, B]
  }

  object AuthenticationDSL {

    trait Authentication

  }

//  trait AuthenticatedOperations extends CommonOperations with CommonTerms {
//    //TODO: The fact that the call requires authentication should be represented by the DSL.
//    //TODO: The implementor of the AuthenticatedOperation should be least bothered by this fact.
//
//
//    type AuthenticatedOperation[A, B] = AuthenticationToken => Try[Operation[A, B]]
//
//    def authenticated[A, B](token: AuthenticationToken)(operation: Operation[A, B]): Try[Operation[A, B]]
//
//    def authenticate(token: AuthenticationToken): Try[Unit]
//
//    def identify[Id](token: AuthenticationToken)(idExtractor: AuthenticationToken => Id): Try[Id] =
//      authenticate(token) map (s => idExtractor(token))
//
//
//    case class AuthenticationException(failureCause: AuthenticationFailureCause) extends Exception(s"Authentication failed. cause: $failureCause")
//
//    trait AuthenticationFailureCause
//
//  }

  trait CommonTerms {
    type AuthenticationToken
  }

}

