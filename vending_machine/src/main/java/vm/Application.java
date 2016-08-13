package vm;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vm.init.TestDataPopulator;

@SpringBootApplication
public class Application {

    @Autowired(required=false)
    private TestDataPopulator testDataPopulator;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void afterConfig() {
        if (testDataPopulator != null) {
        	testDataPopulator.init();
        }
    }
}
