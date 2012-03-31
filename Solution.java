import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

/**
 * @author thiagogenez
 * 
 *         Facebook hiring sample test
 * 
 *         There are K pegs. Each peg can hold discs in decreasing order of
 *         radius when looked from bottom to top of the peg. There are N discs
 *         which have radius 1 to N; Given the initial configuration of the pegs
 *         and the final configuration of the pegs, output the moves required to
 *         transform from the initial to final configuration. You are required
 *         to do the transformations in minimal number of moves.
 * 
 *         A move consists of picking the topmost disc of any one of the pegs
 *         and placing it on top of anyother peg. At anypoint of time, the
 *         decreasing radius property of all the pegs must be maintained.
 * 
 *         Constraints: 1<= N<=8 3<= K<=5
 * 
 *         Input Format: N K 2nd line contains N integers. Each integer in the
 *         second line is in the range 1 to K where the i-th integer denotes the
 *         peg to which disc of radius i is present in the initial
 *         configuration. 3rd line denotes the final configuration in a format
 *         similar to the initial configuration.
 * 
 *         Output Format: The first line contains M - The minimal number of
 *         moves required to complete the transformation. The following M lines
 *         describe a move, by a peg number to pick from and a peg number to
 *         place on. If there are more than one solutions, it's sufficient to
 *         output any one of them. You can assume, there is always a solution
 *         with less than 7 moves and the initial confirguration will not be
 *         same as the final one.
 * 
 *         Sample Input #00:
 * 
 *         2 3 
 *         1 1 
 *         2 2
 * 
 *         Sample Output #00:
 * 
 *         3 
 *         1 3 
 *         1 2 
 *         3 2
 * 
 *         Sample Input #01:
 * 
 *         6 4 
 *         4 2 4 3 1 1 
 *         1 1 1 1 1 1
 * 
 *         Sample Output #01:
 * 
 *         5 
 *         3 1 
 *         4 3 
 *         4 1 
 *         2 1 
 *         3 1
 * 
 *         NOTE: You need to write the full code taking all inputs are from
 *         stdin and outputs to stdout If you are using "Java", the classname is
 *         "Solution"
 *
 */
public class Solution {

	public Solution() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String input[] = br.readLine().split(" ");
			@SuppressWarnings("unused")
			int n = Integer.parseInt(input[0]);
			int k = Integer.parseInt(input[1]);

			String begin_config = br.readLine();
			String end_config = br.readLine();

			Vector<String> moves = breadthFirstSearch(k, begin_config,
					end_config);

			System.out.println(moves.size());
			for (String move : moves) {
				System.out.println(move);
			}
		} catch (Exception e) {
			System.err.println("Error:" + e.getMessage());
			e.printStackTrace();
		}
	}

	private Vector<String> breadthFirstSearch(int k, String begin_config,
			String end_config) {

		Node node = new Node(null, null, begin_config);
		Queue<Solution.Node> queue = new LinkedList<Solution.Node>();
		queue.add(node);

		while (!queue.isEmpty()) {
			node = queue.remove();
			if (!node.isVisited()) {
				node.setVisited(true);
				if (node.getConfig().equals(end_config))
					break;
				ArrayList<Node> nextsNodes = node.getNexts(k);
				queue.addAll(nextsNodes);
			}
		}

		queue.clear();
		queue = null;

		Vector<String> moves = new Vector<String>();
		while (node.getFather() != null) {
			moves.add(node.getLast_move());
			node = node.getFather();
		}
		Collections.reverse(moves);

		return moves;
	}

	public static void main(String args[]) {
		new Solution();
	}

	private class Node {
		private Node father;
		private String last_move;
		private String config;
		private boolean visited = false;

		public Node(Node father, String last_move, String config) {
			super();
			this.father = father;
			this.last_move = last_move;
			this.config = config;
		}

		public ArrayList<Node> getNexts(int k) {
			ArrayList<Node> nexts = new ArrayList<Solution.Node>();
			Vector<Stack<Integer>> pegs = getPegs(k);

			for (int from = 0; from < k; from++) {
				for (int to = 0; to < k; to++) {
					if (from != to) {
						if (!pegs.get(from).isEmpty()
								&& (pegs.get(to).isEmpty() || pegs.get(from)
										.peek() < pegs.get(to).peek())) {

							String s[] = this.config.split(" ");
							s[pegs.get(from).peek() - 1] = String
									.valueOf(to + 1);
							String config = "";
							for (int i = 0; i < s.length; i++) {
								config += s[i] + " ";
							}
							config = config.trim();
							String last_move = String.valueOf(from + 1) + " "
									+ String.valueOf(to + 1);

							nexts.add(new Node(this, last_move, config));
						}
					}
				}
			}
			return nexts;
		}

		private Vector<Stack<Integer>> getPegs(int k) {
			Vector<Stack<Integer>> pegs = new Vector<Stack<Integer>>(k);
			for (int i = 0; i < k; i++) {
				pegs.add(new Stack<Integer>());
			}
			String s[] = this.config.split(" ");
			for (int i = s.length - 1; i >= 0; i--) {
				pegs.get(Integer.parseInt(s[i]) - 1).add(i + 1);
			}
			return pegs;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((config == null) ? 0 : config.hashCode());
			result = prime * result
					+ ((father == null) ? 0 : father.hashCode());
			result = prime * result
					+ ((last_move == null) ? 0 : last_move.hashCode());
			result = prime * result + (visited ? 1231 : 1237);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (config == null) {
				if (other.config != null)
					return false;
			} else if (!config.equals(other.config))
				return false;
			if (father == null) {
				if (other.father != null)
					return false;
			} else if (!father.equals(other.father))
				return false;
			if (last_move == null) {
				if (other.last_move != null)
					return false;
			} else if (!last_move.equals(other.last_move))
				return false;
			if (visited != other.visited)
				return false;
			return true;
		}

		public Node getFather() {
			return father;
		}

		public String getLast_move() {
			return last_move;
		}

		public String getConfig() {
			return config;
		}

		public boolean isVisited() {
			return visited;
		}

		public void setVisited(boolean visited) {
			this.visited = visited;
		}

		private Solution getOuterType() {
			return Solution.this;
		}

	}
}
