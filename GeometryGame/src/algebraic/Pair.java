package algebraic;

public class Pair<A> {
	public final A first;
	public final A second;

	public static <A, B> Pair<A> valueOf(A first, A second) {
		return new Pair<>(first, second);
	}

	private Pair(A first, A second) {
		this.first = first;
		this.second = second;
	}
}
