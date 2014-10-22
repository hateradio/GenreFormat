package tester

import hateradio.genre.Genre

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class GenreSuite extends FunSuite {
  test("idm, edm => IDM; EDM") {
    assert(Genre.format("idm, edm") == "IDM; EDM")
  }

  test("avant-garde => Avant-Garde") {
    assert(Genre.format("avant-garde") == "Avant-Garde")
  }

  test("One hip-pop") {
    assert(Genre.format("hip-pop, Hip-pop") === "Hip-Pop")
  }
}