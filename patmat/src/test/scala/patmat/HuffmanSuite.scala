package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val t2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a', 'b', 'd'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times") {
    assert(times(List('a')) == List(('a', 1)))
    assert(times(List('a', 'a')) == List(('a', 2)))
    assert(times(List('a', 'a', 'c', 'a', 'c', 'a', 'c')) == List(('c', 3), ('a', 4)))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }

  test("until of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(until(singleton, combine)(leaflist)
      === List(Fork(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4), List('e', 't', 'x'), 7)))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("decode and encode longer strings using frenchcode and quickEncode") {
    assert(decode(frenchCode, encode(frenchCode)("thisisatest".toList)) === "thisisatest".toList)
  }

  test("convert t1") {
    new TestTrees {
      val table = convert(t1)
      println("t1: " + t1)
      println("table: " + table)
      assert(decode(frenchCode, encode(frenchCode)("thisisatest".toList)) === "thisisatest".toList)
    }
  }

  test("decode and quick encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, quickEncode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("decode and quick encode longer strings using frenchcode and quickEncode") {
    assert(decode(frenchCode, quickEncode(frenchCode)("thisisatest".toList)) === "thisisatest".toList)
  }

  test("encode and decode a sample string using new custome tree") {
    val myText = "thisisatestofyourcreatecodetreemethod".toList
    val myTree = createCodeTree(myText)
    assert(decode(myTree, encode(myTree)(myText)) === myText)

  }

}
