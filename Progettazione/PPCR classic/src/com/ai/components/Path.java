package com.ai.components;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Path {
	private List<Node> path;

	public List<Node> getPath() {
		return path;
	}

	public Path(List<Node> path) {
		this.path = path;
	}

	public void printPath() {
		for (Node node : path) {
			System.out.println(node);
		}
	}

	public int getRow(int i) // Get row dell'iesimo nodo nel path
	{
		return path.get(i).getRow();
	}

	public int getCol(int i) // Get col dell'iesimo nodo nel path
	{
		return path.get(i).getCol();
	}

	public static Path getPathFromString(String path) {
		StringTokenizer st = new StringTokenizer(path, "]");
		List<Node> res = new ArrayList<Node>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			System.out.println(token);
			if (!token.contains("null")) {
				String numberOnly = token.replaceAll("[^0-9]", "");

				int x = Character.getNumericValue(numberOnly.charAt(0));
				int y = Character.getNumericValue(numberOnly.charAt(1));
				System.out.println("X:" + x + ", Y:" + y);
				res.add(new Node(x, y));
			}
		}
		return new Path(res);
	}

}
