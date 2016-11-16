package com.ciber.skatt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValutadataApplication implements CommandLineRunner {

	@Override
	public void run(String... args) {
    String type = "trans";
    long seed = 1;
    long rows = 1;
    switch (args.length) {
      case 3:
        seed = Long.parseLong(args[2]);
      case 2:
        type = args[1];
      case 1:
        rows = Long.parseLong(args[0]);
        break;
      default:
        System.err.println("Usage: valuta <rows> [<type>] [<seed>]");
        System.exit(0);
    }

    switch (type) {
      case "trans":
        TransGenerator tg = TransGenerator.getInstance();
        tg.setSeed(seed);
        tg.init(new TransGenerator.Config());
        for (int i = 0; i < rows; i++) {
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
