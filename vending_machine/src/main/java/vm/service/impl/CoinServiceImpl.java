package vm.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import vm.entity.Coin;
import vm.exception.MoneyReturnException;
import vm.repository.CoinRepository;
import vm.service.CoinService;

@Service
public class CoinServiceImpl implements CoinService {

	@Autowired
	private CoinRepository coinRepository;
	
	@Override
	public Iterable<Coin> findAll() {
		return coinRepository.findAll();
	}

	@Override
	public Coin findByValue(BigDecimal value) {
		return coinRepository.findByValue(value);
	}

	@Override
	@Transactional
	public void addCoin(BigDecimal value) {
		Coin coin = findByValue(value);
		Preconditions.checkNotNull(coin);
		coin.setCount(coin.getCount() + 1);
	}
		
	@Override
	@Transactional
	public List<BigDecimal> calculateReturnCoins(BigDecimal returnAmount) throws MoneyReturnException {
		List<Coin> coins = coinRepository.findCoinsForReturnOrderByValueDesc(returnAmount);
		List<BigDecimal> result = Lists.newArrayList();
		BigDecimal remaining = returnAmount;
		
		for (Coin coin : coins) {
			int requiredCount = remaining.divideToIntegralValue(coin.getValue()).intValue();
			int usedCount = Integer.min(requiredCount, coin.getCount());
			coin.setCount(coin.getCount() - usedCount);
			remaining = remaining.subtract(new BigDecimal(usedCount).multiply(coin.getValue()));
			for (int i = 0; i < usedCount; i++) {
				result.add(coin.getValue());
			}
			if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
				break;
			}
		}
		
		if (remaining.setScale(2, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) > 0) {
			throw new MoneyReturnException("Not enough coins to return");
		}
		
		return result;
	}

}
