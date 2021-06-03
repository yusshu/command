package team.unnamed.command.annotated.provider.factory;

import team.unnamed.command.annotated.annotation.Infinite;
import team.unnamed.command.annotated.annotation.Quoted;
import team.unnamed.command.annotated.provider.CommandElementFactory;
import team.unnamed.command.parse.CommandElement;
import team.unnamed.command.parse.impl.StringCommandArgument;

import java.lang.reflect.Parameter;

public class StringArgumentFactory
  implements CommandElementFactory {

  @Override
  public CommandElement create(String name, Parameter parameter) {
    Infinite infinite = parameter.getAnnotation(Infinite.class);
    return new StringCommandArgument(
      name,
      parameter.isAnnotationPresent(Quoted.class),
      infinite != null,
      infinite == null ? " " : infinite.value()
    );
  }

}
