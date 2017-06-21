package com.yonatankar.EulerProblems

import scala.reflect.runtime.universe
import java.lang.reflect.Method

/**
 * Created by yonatankar on 7/2/14.
 */
object Main {
  def matchToProblem(id: Int) = f"P$id%03d"

  def main(args: Array[String]) {
    while (true) {
      print("Problem ID: ")
      val id = readLine().toInt

      if (id >= 1 && id <= 20) {
        val results = firstMetaMethod(id)
        println("Result: " + results)
      }
    }
  }

  def firstMetaMethod(id: Int) = {
    val methodId = matchToProblem(id)

    val problemsClass = new Problems()
    val mirror = universe.runtimeMirror(problemsClass.getClass.getClassLoader)
    val instanceMirror = mirror.reflect(problemsClass)
    val methodSymbol = universe.typeOf[Problems].declaration(universe.newTermName(methodId)).asMethod

    val method = instanceMirror.reflectMethod(methodSymbol)
    method()
  }

  def secondMetaMethod(id: Int) = {
    val methodId = matchToProblem(id)

    val problems = new Problems()
    val method = problems.getClass.getMethods.find(_.getName == methodId)
    method match {
      case None => 0
      case _ => method.get.invoke(problems)
    }
  }
}