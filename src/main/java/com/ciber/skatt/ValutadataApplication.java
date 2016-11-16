package com.ciber.skatt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValutadataApplication implements CommandLineRunner {

	@Override
	public void run(String... args) {
		if (args.length < 3) {
			System.err.println("Usage: valuta <type> <seed> <rows>");
			System.exit(2);
		}
		String type = args[0];
		long seed = Long.parseLong(args[1]);
    long rows = Long.parseLong(args[2]);
		switch (type) {
			case "trans":
				TransGenerator tg = TransGenerator.getInstance();
        tg.setSeed(seed);
        tg.init(new TransGenerator.Config());
        for (int i=0 ; i < rows; i++) {
          System.out.println(tg.generate());
        }
				break;
			case "declare":
				break;
			default:
				System.err.println("Unknown type " + type);
				System.exit(2);
				break;
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ValutadataApplication.class, args);
	}
}
