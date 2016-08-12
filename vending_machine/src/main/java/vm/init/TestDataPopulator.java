package vm.init;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vm.entity.Product;
import vm.repository.ProductRepository;

@Component
public class TestDataPopulator {

    @Autowired
    private ProductRepository productRepository;

    public void init() {
    	Product product1 = new Product();
    	product1.setName("Nabisco Mini Snack");
    	product1.setPrice(new BigDecimal(11.65));
    	product1.setCount(25);
    	product1.setCode("1A");
    	productRepository.save(product1);

    	Product product2 = new Product();
    	product2.setName("Kellog's Fruity Snack");
    	product2.setPrice(new BigDecimal(11.43));
    	product2.setCount(10);
    	product2.setCode("1B");
    	productRepository.save(product2);

    	Product product3 = new Product();
    	product3.setName("Veggie Snack");
    	product3.setPrice(new BigDecimal(13.29));
    	product3.setCount(10);
    	product3.setCode("2A");
    	productRepository.save(product3);

    	Product product4 = new Product();
    	product4.setName("Gerber Graduates Puffs");
    	product4.setPrice(new BigDecimal(6.79));
    	product4.setCount(10);
    	product4.setCode("2B");
    	productRepository.save(product4);    	
    }
}
