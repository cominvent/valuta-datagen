package com.ciber.skatt;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ValutadataApplication {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public void run(String... args) throws UnknownHostException {
    boolean elastic = false;
    String esHost = System.getenv("ES_HOST");
    String esIndex = "valuta";
    boolean esClean = false;
    if (esHost != null) {
      elastic = true;
      esClean = Boolean.parseBoolean(System.getenv("ES_CLEAN"));
      if (System.getenv("ES_INDEX") != null) {
        esIndex = System.getenv("ES_INDEX");
      }
    }
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
        log.info("Usage: valuta <rows> [<type>] [<seed>]");
        System.exit(0);
    }

    switch (type) {
      case "trans":
        TransGenerator tg = TransGenerator.getInstance();
        tg.setSeed(seed);
        tg.init(new TransGenerator.Config());
        if (elastic) {
          try {
            log.info("Indexing "+ rows +" docs into ES " + esHost + ":9300/" + esIndex + "/trans");
            TransportClient client = getEsClient(esHost);
            //RestClient client = getEsRestClient(esHost);
            BulkProcessor bulkProcessor = getEsBulkProcessor(client);
            if (esClean) {
              //client.performRequest("DELETE", "/"+esIndex, Collections.singletonMap("pretty", "true"));
              client.delete(new DeleteRequest(esIndex));
            }
            for (int i = 0; i < rows; i++) {
              String doc = tg.generate();
              bulkProcessor.add(new IndexRequest(esIndex, type, ""+i).source(doc));
            }
            bulkProcessor.flush();
            bulkProcessor.close();
          } catch (IOException e) {
            log.error("Failed talking to Elasticsearch: "+e);
          }
        } else {
          for (int i = 0; i < rows; i++) {
            System.out.println(tg.generate());
          }
        }
        break;
      case "declare":
        break;
      default:
        log.error("Unknown type " + type);
        System.exit(2);
        break;
    }
  }

  private BulkProcessor getEsBulkProcessor(Client client) {
    BulkProcessor bulkProcessor = BulkProcessor.builder(
        client,
        new BulkProcessor.Listener() {
          @Override
          public void beforeBulk(long executionId,
                                 BulkRequest request) {
            System.err.print(".");
          }

          @Override
          public void afterBulk(long executionId,
                                BulkRequest request,
                                BulkResponse response) {}

          @Override
          public void afterBulk(long executionId,
                                BulkRequest request,
                                Throwable failure) {
            System.err.println("BulkFailure " + failure);
          }
        })
        .setBulkActions(10000)
        .setBulkSize(new ByteSizeValue(100, ByteSizeUnit.MB))
        .setFlushInterval(TimeValue.timeValueSeconds(5))
        .setConcurrentRequests(1)
        .setBackoffPolicy(
            BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
        .build();
    return bulkProcessor;
  }

  private TransportClient getEsClient(String esHost) throws UnknownHostException {
    return new PreBuiltTransportClient(Settings.EMPTY)
            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost), 9300));
//    return new TransportClient.Builder().settings(Settings.EMPTY).build()
//            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost), 9300));
  }

  private RestClient getEsRestClient(String esHost) throws UnknownHostException {
    return RestClient.builder(
            new HttpHost("localhost", 9200, "http")).build();  
  }
  
  public static void main(String[] args) throws UnknownHostException {
		new ValutadataApplication().run(args);
	}
}
