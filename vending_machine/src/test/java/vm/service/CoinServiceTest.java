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
	public void shouldReturnOptimalCoins() throws MoneyReturnException {
		List<BigDecimal> list = coinService.calculateReturnCoins(new BigDecimal(0.81));
		
		assertThat(list.size()).isEqualTo(4);
		assertThat(list.get(0).doubleValue()).isEqualTo(0.5);
		assertThat(list.get(1).doubleValue()).isEqualTo(0.2);
		assertThat(list.get(2).doubleValue()).isEqualTo(0.1);
		assertThat(list.get(3).doubleValue()).isEqualTo(0.01);
	}
	
	@Test(expected=MoneyReturnException.class)
	public void shouldThrowMoneyReturnException() throws MoneyReturnException {
		coinService.calculateReturnCoins(new BigDecimal(0.99));
		coinService.calculateReturnCoins(new BigDecimal(0.99));
	}
}


