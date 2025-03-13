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

    Set<String> visited = new HashSet<>();
    pSWHelper(vertex, k, visited);
  }

  private static void pSWHelper(Vertex<String> vertex, int k, Set<String> visited) {

    if (vertex == null) {
      return;
    }

    if (visited.contains(vertex.data)) {
      return;
    }

    if (vertex.data.length() < k) {
      System.out.println(vertex.data);
      visited.add(vertex.data);
    }

    for (var neighbor : vertex.neighbors) {
      pSWHelper(neighbor, k, visited);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {

    Set<String> visited = new HashSet<>();

    return lWHelper(vertex, visited, "");
  }

  private static String lWHelper(Vertex<String> vertex, Set<String> visited, String longestWord) {

    if (vertex == null || visited.contains(vertex.data)) {
      return longestWord;
    }

    visited.add(vertex.data);

    if (vertex.data.length() > longestWord.length()) {
      longestWord = vertex.data;
    }

    String currentLongest = vertex.data;

    for (var neighbor : vertex.neighbors) {
      currentLongest = lWHelper(neighbor, visited, longestWord);
      
      if (currentLongest.length() > longestWord.length()) {
        longestWord = currentLongest;
      }
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

    Set<T> visited = new HashSet<>();
    pSLHelper(vertex, visited);
  }

  private static <T> void pSLHelper(Vertex<T> vertex, Set<T> visited) {
    if (vertex == null) {
      return;
    }

    if (visited.contains(vertex.data)) {
      System.out.println(vertex.data);
      return;
    }

    visited.add(vertex.data);

    for (var neighbor : vertex.neighbors) {
      pSLHelper(neighbor, visited);
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
    Set<Airport> visited = new HashSet<>();
    return cRHelper(start, destination, visited);
  }

  public static boolean cRHelper(Airport start, Airport destination, Set<Airport> visited) {

   if (start == null || visited.contains(start)) {
      return false;
    }

    if (start == destination) {
      return true;
    }

    visited.add(start);

    for (Airport flight : start.getOutboundFlights()) {
      if (cRHelper(flight, destination, visited)) {
        return true;
      }
    }

    return false;
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
    Set<T> visited = new HashSet<>();
    unreachableHelper(graph, starting, visited);

    Set<T> cantVisit = new HashSet<>(graph.keySet());
    cantVisit.removeAll(visited);

    return cantVisit;
  }

  public static <T> void unreachableHelper(Map<T, List<T>> graph, T starting, Set<T> visited) {

    if (!graph.containsKey(starting) || graph == null || !visited.add(starting)) {
      return;
    }

    for (var neighbor : graph.get(starting)) {
      unreachableHelper(graph, neighbor, visited);
    }
  }
}
