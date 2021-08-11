package team.unnamed.command;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Simple container of object by
 * type and name
 */
public class Namespace {

  private final Map<Key, Object> objects = new HashMap<>();

  /** Sets the {@code object} for the given {@code type} and {@code name} */
  public void setObject(Type type, String name, Object object) {
    objects.put(new Key(type, name), object);
  }

  /** Returns the object for the given {@code type} and {@code name} */
  public <T> T getObject(Type type, String name) {
    @SuppressWarnings("unchecked")
    T value = (T) objects.get(new Key(type, name));
    return value;
  }

  /**
   * Represents a {@link Namespace} entry key,
   * it's a combination of a Java Type and an
   * arbitrary string.
   */
  private static class Key {

    private final Type type;
    private final String name;

    private Key(Type type, String name) {
      this.type = type;
      this.name = name;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Key key = (Key) o;
      return type.equals(key.type)
        && name.equals(key.name);
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, name);
    }
  }

}
