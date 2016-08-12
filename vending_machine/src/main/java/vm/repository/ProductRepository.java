package vm.repository;

import org.springframework.data.repository.CrudRepository;

import vm.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	Iterable<Product> findAll();
	
}
