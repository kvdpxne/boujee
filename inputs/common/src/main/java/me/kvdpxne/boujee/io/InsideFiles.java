package me.kvdpxne.boujee.io;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.function.BiFunction;

/**
 * Utility class for accessing files inside archives, such as JAR or ZIP files,
 * that are available on the classpath.
 * <p>
 * This class is designed to provide an abstraction for working with files
 * inside containers by utilizing the Java NIO file system API. It allows you to
 * process files using a provided {@link BiFunction} without manually managing
 * file systems or paths.
 * <p>
 * The class is non-instantiable and provides only static utility methods.
 *
 * @since 0.1.0
 */
public final class InsideFiles {

  /**
   * Private constructor to prevent instantiation of this utility class.
   *
   * @throws AssertionError Always, as this class cannot be instantiated.
   * @since 0.1.0
   */
  private InsideFiles() {
    throw new AssertionError("");
  }

  /**
   * Accesses a file inside a container (e.g., JAR or ZIP file) located on the
   * classpath and applies a function to process it.
   * <p>
   * This method locates the resource using the provided {@link ClassLoader},
   * opens a {@link FileSystem} for the container, and applies the given
   * {@link BiFunction} to the resolved {@link Path} inside the container.
   * <p>
   * The resource path must follow the JAR URL format, i.e., a path that may
   * include an internal structure separated by `!` (e.g.,
   * `jar:file:/example.jar!/content`).
   *
   * @param <T>         The return type of the function applied to the file.
   * @param classLoader The {@link ClassLoader} to use for locating the
   *                    resource.
   * @param path        The relative path of the resource to be accessed inside
   *                    the container.
   * @param function    A {@link BiFunction} that processes the
   *                    {@link FileSystem} and the {@link Path} to produce a
   *                    result.
   * @return The result of the {@code function} applied to the file.
   * @throws IllegalArgumentException If the resource cannot be found using the
   *                                  given path.
   * @throws RuntimeException         If an {@link IOException} occurs while
   *                                  accessing the file system.
   * @since 0.1.0
   */
  public static <T> T files(
    final ClassLoader classLoader,
    final String path,  // np. "assets/languages"
    final BiFunction<FileSystem, Path, T> function
  ) {
    URL resource = classLoader.getResource(path);
    if (resource == null) throw new IllegalArgumentException("...");

    try {
      URI uri = resource.toURI();

      if ("jar".equals(uri.getScheme())) {
        // Jesteśmy w JAR — bezpiecznie otwieramy
        try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
          return function.apply(fs, fs.getPath(path));
        }
      } else {
        // Jesteśmy w katalogu (np. target/classes) — nie tworzymy osobnego FileSystem
        Path p = Paths.get(uri);
        if (!Files.exists(p))
          throw new IllegalArgumentException("Not found: " + p);

        // Używamy domyślnego systemu plików
        return function.apply(FileSystems.getDefault(), p);
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to open: " + path, e);
    }
  }
}
