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

    Set<Vertex<String>> visited = new HashSet<>();
    printShortWordsHelper(vertex, k, visited);
  }

  private static void printShortWordsHelper(Vertex<String> vertex, int k, Set<Vertex<String>> visited) {
    if (vertex == null || visited.contains(vertex)) return;

    visited.add(vertex);

    String value = vertex.data;
    if (value.length() < k) {
      System.out.println(value);
    }

    for (Vertex<String> neighbor : vertex.neighbors) {
      printShortWordsHelper(neighbor, k, visited);
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

    Set<Vertex<String>> visited = new HashSet<>();
    return longestWordHelper(vertex, visited);
  }

  private static String longestWordHelper(Vertex<String> vertex, Set<Vertex<String>> visited) {
    if (vertex == null || visited.contains(vertex)) return "";
    
    visited.add(vertex);

    String longestWord = vertex.data;

    for (Vertex<String> neighbor : vertex.neighbors) {
      String longestNeighbor = longestWordHelper(neighbor, visited);
      if (longestNeighbor.length() > longestWord.length()) {
        longestWord = longestNeighbor;
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
    if (vertex == null) return;

    Set<Vertex<T>> visited = new HashSet<>();
    printSelfLoopersHelper(vertex, visited);
  }

  private static <T> void printSelfLoopersHelper(Vertex<T> vertex, Set<Vertex<T>> visited) {
    if (vertex == null || visited.contains(vertex)) return;  

    visited.add(vertex);

    if (vertex.neighbors.contains(vertex)) {
      System.out.println(vertex.data);
    }

    for (Vertex<T> neighbor : vertex.neighbors) {
      printSelfLoopersHelper(neighbor, visited);
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
    if (start == null || destination == null) return false;
    if (start.equals(destination)) return true;

    Set<Airport> visited = new HashSet<>();
    return canReachHelper(start, destination, visited);
  }

  private static boolean canReachHelper(Airport current, Airport destination, Set<Airport> visited) {
    if (visited.contains(current)) return false; 
    if (current.equals(destination)) return true;

    visited.add(current);

    for (Airport neighbor : current.getOutboundFlights()) {
      if (canReachHelper(neighbor, destination, visited)) {
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
    if (graph == null || !graph.containsKey(starting)) {
      return new HashSet<>(graph.keySet());
    }

    Set<T> reachable = new HashSet<>();
    unreachableHelper(graph, starting, reachable);

    Set<T> unreachable = new HashSet<>(graph.keySet());
    unreachable.removeAll(reachable);

    return unreachable;  
  }

  private static <T> void unreachableHelper(Map<T, List<T>> graph, T current, Set<T> reachable) {
    if (reachable.contains(current)) return;  

    reachable.add(current);  

    for (T neighbor : graph.get(current)) {
        unreachableHelper(graph, neighbor, reachable);  
    }
  }
}
