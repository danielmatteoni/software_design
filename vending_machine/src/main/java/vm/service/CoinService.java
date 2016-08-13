package vm.service;

import java.math.BigDecimal;
import java.util.List;

import vm.entity.Coin;
import vm.exception.MoneyReturnException;

public interface CoinService {

	Iterable<Coin> findAll();
	
	Coin findByValue(BigDecimal value);
	
	void addCoin(BigDecimal value);
	
	List<BigDecimal> calculateReturnCoins(BigDecimal returnAmount) throws MoneyReturnException;
}
