package controllers

import play.api.mvc._
import scala.concurrent.Future
import neo4jplugin.{Neo4jPlugin, Neo4jServiceProvider}

/**
 * Created by tuxburner on 5/14/14.
 */
object Neo4jTransactionAction extends ActionBuilder[Request]  {
  override def composeAction[A](action: Action[A]) = new Neo4jTransactionAction(action)

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    block(request)
  }
}

case class Neo4jTransactionAction[A](action: Action[A]) extends Action[A] {

  def apply(request: Request[A]): Future[Result] = {
    /*val serviceProvider: Neo4jServiceProvider = Neo4jPlugin.get();
    val tx = serviceProvider.template.getGraphDatabase.beginTx;
    try {
      val result = action(request)
      tx.success()
      result
    }
    catch {
      case t: Throwable => {
        tx.failure();
        throw t
      }
    } finally {
      tx.close();
    } */
    val result = action(request)
    result
  }

  lazy val parser = action.parser
}