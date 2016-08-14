package vm.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import vm.entity.Coin;
import vm.exception.MoneyReturnException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinServiceTest {

	@Autowired
	private CoinService coinService;
	
	@Test
	public void shouldIncreaseCoinCount() {
		Coin coin = coinService.findByValue(BigDecimal.ONE);
		int count = coin.getCount();
		
		coinService.addCoin(BigDecimal.ONE);
		
		coin = coinService.findByValue(BigDecimal.ONE);
		assertThat(coin.getCount()).isEqualTo(count + 1);
	}
	
	@Test
	public void shouldReturnOptimalCoinsAfterPurchase() throws MoneyReturnException {
		// Given
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		
		// When
		List<BigDecimal> list = coinService.purchaseProduct(1L);
		
		// Then
		assertThat(list.size()).isEqualTo(3);
		assertThat(list.get(0).doubleValue()).isEqualTo(0.2);
		assertThat(list.get(1).doubleValue()).isEqualTo(0.1);
		assertThat(list.get(2).doubleValue()).isEqualTo(0.05);
	}

	@Test
	public void shouldWithdrawWholeCredit() throws MoneyReturnException {
		// Given
		coinService.addCoin(new BigDecimal(2));
		
		// When
		List<BigDecimal> list = coinService.withdrawCredit();
		
		// Then
		assertThat(list.get(0).doubleValue()).isEqualTo(2);
	}
		
	@Test(expected=MoneyReturnException.class)
	public void shouldThrowMoneyReturnExceptionIfNoCoinsAvailable() throws MoneyReturnException {
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.purchaseProduct(4L);
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.purchaseProduct(4L);
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.addCoin(new BigDecimal(2));
		coinService.purchaseProduct(4L);		
	}
	
}


