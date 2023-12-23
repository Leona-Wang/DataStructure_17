



public class Product{
	
	private String productName;
	private String productUrl;
	private int productPrice;
	
	public Product(String productName,String productUrl,int productPrice) {
		
		this.productName=productName;
		this.productUrl=productUrl;
		this.productPrice=productPrice;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public int getProductPrice() {
		return productPrice;
	}
	
	public String getProductUrl() {
		return productUrl;
	}
	
	
	
	
}