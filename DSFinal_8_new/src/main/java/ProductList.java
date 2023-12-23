import java.util.ArrayList;

public class ProductList{
	
	
	private Product product;
	private ArrayList<Product> products;
	private ArrayList<Product> productList;
	private ArrayList<Integer> prices;
	private Sort sort;
	
	public ProductList() {
		
		this.products=new ArrayList<Product>();
		
	}
	
	public void addProduct(Product p) {
		
		products.add(p);
		prices.add(p.getProductPrice());
	}
	
	public ArrayList<Product> sortProduct(){
		
		sort=new Sort(prices);
		
		for (int i:prices) {
			
			for (Product p:products) {
				
				if (p.getProductPrice()==i) {
					productList.add(p);
					break;
				}
			}
		}
		
		return productList;
	}
	
	
	
	
}