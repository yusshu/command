package team.unnamed.command.annotated.provider;

import team.unnamed.command.parse.CommandElement;

import java.lang.reflect.Parameter;

public interface CommandElementFactory {

  CommandElement create(String name, Parameter parameter);

}
