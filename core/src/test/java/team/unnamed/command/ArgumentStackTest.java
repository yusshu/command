package team.unnamed.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import team.unnamed.command.stack.ArgumentStack;

public class ArgumentStackTest {

  @Test
  public void testNormal() {
    ArgumentStack stack = ArgumentStack.splitBySpace("    this   is an  example  ");
    Assertions.assertEquals("this", stack.next());
    Assertions.assertEquals("is", stack.next());
    Assertions.assertEquals("an", stack.next());
    Assertions.assertEquals("example", stack.next());
    Assertions.assertFalse(stack.hasNext());
  }

  @Test
  public void testQuoted() {
    ArgumentStack stack = ArgumentStack.splitBySpace(" this  'is an example'    too");
    Assertions.assertEquals("this", stack.next());
    Assertions.assertEquals("is an example", stack.nextQuoted());
    Assertions.assertEquals("too", stack.nextQuoted());
    Assertions.assertFalse(stack.hasNext());
  }

  @Test
  public void testEscapingQuoted() {
    ArgumentStack stack = ArgumentStack.splitBySpace(
      "this 'is an' example \"using double''''quotes and\" escaping 'single \\' quotes' lol"
    );

    Assertions.assertEquals("this", stack.next());
    Assertions.assertEquals("is an", stack.nextQuoted());
    Assertions.assertEquals("example", stack.next());
    Assertions.assertEquals("using double''''quotes and", stack.nextQuoted());
    Assertions.assertEquals("escaping", stack.next());
    Assertions.assertEquals("single ' quotes", stack.nextQuoted());
    Assertions.assertEquals("lol", stack.next());
    Assertions.assertFalse(stack.hasNext());
  }

}
