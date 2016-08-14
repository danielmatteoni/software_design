package vm.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import vm.entity.Coin;
import vm.entity.Product;
import vm.service.CoinService;
import vm.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(PurchaseController.class)
public class PurchaseControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private ProductService productService;

	@MockBean
	private CoinService coinService;
	
	@Before
	public void setUp() {
		given(this.productService.findAll()).willReturn(prepareProducts());
		given(this.coinService.findAll()).willReturn(prepareCoins());
	}
		
	@Test
	public void shouldListProducts() throws Exception {
		given(this.coinService.getCredit())
			.willReturn(BigDecimal.ZERO);
		
		// Show product list and expect all products to be listed
		this.mvc.perform(get("/")).andExpect(status().isOk())
			.andExpect(content().string(containsString("Nabisco Mini Snack")))
			.andExpect(content().string(containsString("Kellog&#39;s Fruity Snack")))
			.andExpect(content().string(containsString("Veggie Snack")))
			.andExpect(content().string(containsString("Gerber Graduates Puffs")));
	}
	
	@Test
	public void shouldPurchaseProductAndReturnCoins() throws Exception {
		given(this.productService.findOne(1L))
			.willReturn(new Product("Nabisco Mini Snack", new BigDecimal(11.65), 25));
		given(this.coinService.getCredit())
			.willReturn(new BigDecimal(12));
		List<BigDecimal> coins = Lists.newArrayList();
		coins.add(new BigDecimal(0.2));
		coins.add(new BigDecimal(0.1));
		coins.add(new BigDecimal(0.05));
		given(this.coinService.purchaseProduct(1L))
			.willReturn(coins);
		
		// Order product Nabisco Mini Snack for 11.65
		this.mvc.perform(get("/product?productId=1")).andExpect(status().isOk())
			.andExpect(content().string(containsString("Product purchased")))
			.andExpect(content().string(containsString("Returned coins:")))
			.andExpect(content().string(containsString("0.20")))
			.andExpect(content().string(containsString("0.10")))
			.andExpect(content().string(containsString("0.05")));
	}
	
	@Test
	public void shouldWithdrawCredit() throws Exception {
		given(this.coinService.getCredit())
			.willReturn(new BigDecimal(3));
		List<BigDecimal> coins = Lists.newArrayList();
		coins.add(new BigDecimal(2));
		coins.add(new BigDecimal(1));
		given(this.coinService.withdrawCredit())
			.willReturn(coins);
		
		// Withdraw and expect coins returned
		this.mvc.perform(get("/withdraw")).andExpect(status().isOk())
			.andExpect(content().string(containsString("Returned coins:")))
			.andExpect(content().string(containsString("2")))
			.andExpect(content().string(containsString("1")));
	}
	
	private Iterable<Product> prepareProducts() {
    	List<Product> list = Lists.newArrayList();
		
		Product product1 = new Product();
    	product1.setName("Nabisco Mini Snack");
    	product1.setPrice(new BigDecimal(11.65));
    	product1.setCount(25);
    	list.add(product1);

    	Product product2 = new Product();
    	product2.setName("Kellog's Fruity Snack");
    	product2.setPrice(new BigDecimal(11.43));
    	product2.setCount(10);
    	list.add(product2);

    	Product product3 = new Product();
    	product3.setName("Veggie Snack");
    	product3.setPrice(new BigDecimal(8));
    	product3.setCount(1);
    	list.add(product3);

    	Product product4 = new Product();
    	product4.setName("Gerber Graduates Puffs");
    	product4.setPrice(new BigDecimal(6.01));
    	product4.setCount(10);
    	list.add(product4);
    	
    	return list;
	}
	
	private Iterable<Coin> prepareCoins() {
		List<Coin> list = Lists.newArrayList();
		
    	Coin coin1 = new Coin();
    	coin1.setValue(new BigDecimal(2));
    	coin1.setCount(0);
    	list.add(coin1);
    	
    	Coin coin2 = new Coin();
    	coin2.setValue(new BigDecimal(1));
    	coin2.setCount(1);
    	list.add(coin2);
    	
    	Coin coin3 = new Coin();
    	coin3.setValue(new BigDecimal(0.5));
    	coin3.setCount(2);
    	list.add(coin3);
    	
    	Coin coin4 = new Coin();
    	coin4.setValue(new BigDecimal(0.2));
    	coin4.setCount(3);
    	list.add(coin4);

    	Coin coin5 = new Coin();
    	coin5.setValue(new BigDecimal(0.1));
    	coin5.setCount(4);
    	list.add(coin5);
    	
    	Coin coin6 = new Coin();
    	coin6.setValue(new BigDecimal(0.05));
    	coin6.setCount(5);
    	list.add(coin6);

    	Coin coin7 = new Coin();
    	coin7.setValue(new BigDecimal(0.02));
    	coin7.setCount(6);
    	list.add(coin7);

    	Coin coin8 = new Coin();
    	coin8.setValue(new BigDecimal(0.01));
    	coin8.setCount(7);
    	list.add(coin8);    	

    	return list;
	}
	
}
