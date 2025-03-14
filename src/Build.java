import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    if (vertex == null) return;
    printShortWords(vertex, k, new HashSet<Vertex<String>>());
  }

  public static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> record) {
    if (record.contains(vertex)) return;
    record.add(vertex);

    if (vertex.data.length() < k) {
      System.out.println(vertex.data);
    }

    for (var neighbor: vertex.neighbors) {
      printShortWords(neighbor, k, record);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    if (vertex == null) return "";
    String longestWord = vertex.data;
    longestWord = longestWord(vertex, longestWord, new HashSet<Vertex<String>>());
    return longestWord;
  }

  public static String longestWord(Vertex<String> vertex, String longestWord, HashSet<Vertex<String>> visited) {
    if (visited.contains(vertex)) return longestWord;
    visited.add(vertex);

    if (vertex.data.length() > longestWord.length()) longestWord = vertex.data;

    for (var neighbor: vertex.neighbors) {
      longestWord = longestWord(neighbor, longestWord, visited);
    }

    return longestWord;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    if (vertex == null) return;
    printSelfLoopers(vertex, new HashSet<Vertex<T>>());
  }

  public static <T> void printSelfLoopers(Vertex<T> vertex, HashSet<Vertex<T>> record) {
    if (record.contains(vertex)) return;
    record.add(vertex);

    if (vertex.neighbors.contains(vertex)) {
      System.out.println(vertex.data);
    }

    for (var neighbor: vertex.neighbors) {
      printSelfLoopers(neighbor, record);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    return canReach(start, destination, new HashSet<Airport>(), false);
  }

  public static boolean canReach(Airport start, Airport destination, HashSet<Airport> record, boolean reachable) {
    if (record.contains(start)) return reachable;
    record.add(start);

    if (start == destination) return true;

    for (var connection: start.getOutboundFlights()) {
      reachable = canReach(connection, destination, record, reachable);
    }

    return reachable;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    // create a set containing all the values
    Set<T> unreachableValues = new HashSet<>();
    for (T key: graph.keySet()) {
      unreachableValues.add(key);
    }

    if (!unreachableValues.contains(starting)) return unreachableValues;

    unreachable(graph, starting, new HashSet<T>(), unreachableValues);

    return unreachableValues;
  }

  public static <T> void unreachable(Map<T, List<T>> graph, T current, Set<T> record, Set<T> unreachableValues) {
    if (record.contains(current)) return;
    record.add(current);
    unreachableValues.remove(current);

    for (var neighbor: graph.get(current)) {
      unreachable(graph, neighbor, record, unreachableValues);
    }

  }
}
