package vm.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import vm.entity.Coin;

public interface CoinRepository extends CrudRepository<Coin, Long> {

	Coin findByValue(BigDecimal value);
	
	@Query("select c from Coin c where c.count > 0 and c.value <= :returnValue order by c.value desc")
	List<Coin> findCoinsForReturnSortByValueDesc(@Param("returnValue") BigDecimal returnValue);
	
}
