package dev.vlxd.dataforge.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataForgeRunnerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DataForgeRunnerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
