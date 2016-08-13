package vm.service;

import vm.entity.Product;

public interface ProductService {

	Iterable<Product> findAll();
	
	Product findOne(Long id);
}
