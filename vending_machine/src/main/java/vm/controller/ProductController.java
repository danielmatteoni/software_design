package vm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;

import vm.entity.Product;
import vm.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping({"/","/products"})
	public String showProducts() {
	    return "products";
	}
	
	@ModelAttribute("allProducts")
	public List<Product> populateProducts() {
	    return Lists.newArrayList(productService.findAll());
	}
	
}
