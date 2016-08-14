package vm.init;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vm.entity.Coin;
import vm.entity.Product;
import vm.repository.CoinRepository;
import vm.repository.ProductRepository;

@Component
public class TestDataPopulator {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CoinRepository coinRepository;

    public void init() {
    	Product product1 = new Product();
    	product1.setName("Nabisco Mini Snack");
    	product1.setPrice(new BigDecimal(11.65));
    	product1.setCount(25);
    	productRepository.save(product1);

    	Product product2 = new Product();
    	product2.setName("Kellog's Fruity Snack");
    	product2.setPrice(new BigDecimal(11.43));
    	product2.setCount(10);
    	productRepository.save(product2);

    	Product product3 = new Product();
    	product3.setName("Veggie Snack");
    	product3.setPrice(new BigDecimal(8));
    	product3.setCount(1);
    	productRepository.save(product3);

    	Product product4 = new Product();
    	product4.setName("Gerber Graduates Puffs");
    	product4.setPrice(new BigDecimal(6.01));
    	product4.setCount(10);
    	productRepository.save(product4);    	
    	
    	Coin coin1 = new Coin();
    	coin1.setValue(new BigDecimal(2));
    	coin1.setCount(0);
    	coinRepository.save(coin1);
    	
    	Coin coin2 = new Coin();
    	coin2.setValue(new BigDecimal(1));
    	coin2.setCount(1);
    	coinRepository.save(coin2);
    	
    	Coin coin3 = new Coin();
    	coin3.setValue(new BigDecimal(0.5));
    	coin3.setCount(2);
    	coinRepository.save(coin3);
    	
    	Coin coin4 = new Coin();
    	coin4.setValue(new BigDecimal(0.2));
    	coin4.setCount(3);
    	coinRepository.save(coin4);

    	Coin coin5 = new Coin();
    	coin5.setValue(new BigDecimal(0.1));
    	coin5.setCount(4);
    	coinRepository.save(coin5);
    	
    	Coin coin6 = new Coin();
    	coin6.setValue(new BigDecimal(0.05));
    	coin6.setCount(5);
    	coinRepository.save(coin6);

    	Coin coin7 = new Coin();
    	coin7.setValue(new BigDecimal(0.02));
    	coin7.setCount(6);
    	coinRepository.save(coin7);

    	Coin coin8 = new Coin();
    	coin8.setValue(new BigDecimal(0.01));
    	coin8.setCount(7);
    	coinRepository.save(coin8);    	
    }
}
