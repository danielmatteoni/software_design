package vm.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import vm.entity.Coin;
import vm.entity.Product;
import vm.exception.MoneyReturnException;
import vm.service.CoinService;
import vm.service.ProductService;

@Controller
public class PurchaseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductService productService;

	@Autowired
	private CoinService coinService;

	@RequestMapping({ "/", "/choose_product" })
	public String chooseProduct(Model model) {
		model.addAttribute("credit", coinService.getCredit());
		return "choose_product";
	}

	@RequestMapping({ "/withdraw" })
	public String withdrawCredit(Model model) {
		List<BigDecimal> returnCoins;
		try {
			returnCoins = coinService.withdrawCredit();
		} catch (MoneyReturnException e) {
			model.addAttribute("errorMessage", e.getMessage());
			logger.error("Error while returning money", e);
			return "return_error";
		}
		model.addAttribute("credit", coinService.getCredit());
		model.addAttribute("returnCoins", returnCoins);
		return "withdraw";
	}
	
	@RequestMapping({ "/product" })
	public String showProduct(Model model, @RequestParam(value = "productId", required = true) String id,
			@RequestParam(value = "value", required = false) String value) {
		Preconditions.checkArgument(NumberUtils.isNumber(id));
		Long productId = new Long(id);
		Product product = productService.findOne(productId);
		
		if (value != null) {
			Preconditions.checkArgument(NumberUtils.isNumber(value));
			coinService.addCoin(new BigDecimal(value));
		}		
		
		try {
			if (coinService.getCredit().compareTo(product.getPrice()) >= 0) {
				List<BigDecimal> returnCoins = coinService.purchaseProduct(productId);
				populateProductAndCreditModel(model, product);
				model.addAttribute("returnCoins", returnCoins);
				return "purchase";
			} else {
				populateProductAndCreditModel(model, product);
				return "product";
			}
		} catch (MoneyReturnException e) {
			model.addAttribute("errorMessage", e.getMessage());
			logger.error("Error while returning money", e);
			return "return_error";
		} 
	}

	private void populateProductAndCreditModel(Model model, Product product) {
		model.addAttribute("productId", product.getId());
		model.addAttribute("name", product.getName());
		model.addAttribute("price", product.getPrice());
		model.addAttribute("credit", coinService.getCredit());
	}

	@ModelAttribute("allProducts")
	public List<Product> populateProductsOnStock() {
		return StreamSupport.stream(productService.findAll().spliterator(), false).filter(p -> p.getCount() > 0)
				.collect(Collectors.toList());
	}

	@ModelAttribute("allCoins")
	public List<Coin> populateCoinTypes() {
		return Lists.newArrayList(coinService.findAll());
	}
	
}
