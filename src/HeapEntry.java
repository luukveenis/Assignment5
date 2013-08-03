public class HeapEntry implements Comparable<HeapEntry>{
	
	int edgeWeight;
	int startVertex;
	int endVertex;

	public HeapEntry(int weight, int start, int end){
		this.edgeWeight = weight;
		this.startVertex = start;
		this.endVertex = end;
	}


	@Override
	public int compareTo(HeapEntry other) {
		if (edgeWeight < other.edgeWeight){
			return -1;
		}
		else if (edgeWeight == other.edgeWeight){
			return 0;
		}
		else {
			return 1;
		}
	}
	
}
