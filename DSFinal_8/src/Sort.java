import java.util.ArrayList;

public class Sort{
	
	private ArrayList<Integer> arr=new ArrayList<Integer>();
	private int n;
	
	
	public Sort(ArrayList<Integer> arr) {
		
		this.arr=arr;
		n=arr.size();
		mergeSort(arr,0,n-1);
		
	}
	
	public ArrayList<Integer> getArr(){
		return arr;
	}
	
	public void merge(ArrayList<Integer> arr,int head,int mid,int tail) {
		
		int lenA=mid-head+1;
		int lenB=tail-(mid+1)+1;
		int[] A=new int[lenA];
		int[] B=new int[lenB];
		int i,j;
		
		for (i=0;i<lenA;i++) {
			A[i]=arr.get(head+i);
		}
		for (j=0;j<lenB;j++) {
			B[j]=arr.get(mid+1+j);
		}
		
		i=0;
		j=0;
		int k=head;
		
		while(i<lenA && j<lenB) {
			
			if (A[i]<B[j]) {
				arr.remove(k);
				arr.add(k, A[i]);
				i++;
				k++;
			}
			else {
				arr.remove(k);
				arr.add(k, B[j]);
				j++;
				k++;
				
			}
		}
		
		while(i<lenA) {
			arr.remove(k);
			arr.add(k,A[i]);
			i++;
			k++;
		}
		
		while(j<lenB) {
			arr.remove(k);
			arr.add(k,B[j]);
			j++;
			k++;
		}
		
		
		
		
	}
	
	public void mergeSort(ArrayList<Integer> arr,int head,int tail) {
		
		if (head<tail) {
			int mid=(head+tail)/2;
			mergeSort(arr,head,mid);
			mergeSort(arr,mid+1,tail);
			merge(arr,head,mid,tail);
		}
		
	}
	
	
	
	
	
	
}