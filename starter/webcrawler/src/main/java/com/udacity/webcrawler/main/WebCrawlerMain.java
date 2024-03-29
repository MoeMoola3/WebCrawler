package com.udacity.webcrawler.main;

import com.google.inject.Guice;
import com.udacity.webcrawler.WebCrawler;
import com.udacity.webcrawler.WebCrawlerModule;
import com.udacity.webcrawler.json.ConfigurationLoader;
import com.udacity.webcrawler.json.CrawlResult;
import com.udacity.webcrawler.json.CrawlResultWriter;
import com.udacity.webcrawler.json.CrawlerConfiguration;
import com.udacity.webcrawler.profiler.Profiler;
import com.udacity.webcrawler.profiler.ProfilerModule;

import javax.inject.Inject;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class WebCrawlerMain {

  private final CrawlerConfiguration config;

  private WebCrawlerMain(CrawlerConfiguration config) {
    this.config = Objects.requireNonNull(config);
  }

  @Inject
  private WebCrawler crawler;

  @Inject
  private Profiler profiler;

  private void run() throws Exception {
    Guice.createInjector(new WebCrawlerModule(config), new ProfilerModule()).injectMembers(this);

    CrawlResult result = crawler.crawl(config.getStartPages());
    CrawlResultWriter resultWriter = new CrawlResultWriter(result);
    // TODO: Write the crawl results to a JSON file (or System.out if the file name is empty) REFACTOR
    if(config.getResultPath().isEmpty()){
      Writer outputWriter = new OutputStreamWriter(System.out);
      resultWriter.write(outputWriter);
    }
    else{
      resultWriter.write(Paths.get(config.getResultPath())); //
    }
    // TODO: Write the profile data to a text file (or System.out if the file name is empty)
    if(config.getProfileOutputPath().isEmpty()){
//      Path profileOutputPath = Path.of(config.getProfileOutputPath());
      OutputStreamWriter profileOutputPath = new OutputStreamWriter(System.out);;
      profiler.writeData(profileOutputPath);
    }
    else{
      profiler.writeData(Path.of(config.getProfileOutputPath()));
    }

  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println("Usage: WebCrawlerMain [starting-url]");
      return;
    }

    CrawlerConfiguration config = new ConfigurationLoader(Path.of(args[0])).load();
    new WebCrawlerMain(config).run();
  }
}
//
//package com.udacity.webcrawler.main;
//import com.google.inject.Guice;
//import com.udacity.webcrawler.WebCrawler;
//import com.udacity.webcrawler.WebCrawlerModule;
//import com.udacity.webcrawler.json.ConfigurationLoader;
//import com.udacity.webcrawler.json.CrawlResult;
//import com.udacity.webcrawler.json.CrawlResultWriter;
//import com.udacity.webcrawler.json.CrawlerConfiguration;
//import com.udacity.webcrawler.profiler.Profiler;
//import com.udacity.webcrawler.profiler.ProfilerModule;
//import javax.inject.Inject;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Objects;
//public final class WebCrawlerMain {
//  private final CrawlerConfiguration config;
//  private WebCrawlerMain(CrawlerConfiguration config) {
//    this.config = Objects.requireNonNull(config);
//  }
//  @Inject
//  private WebCrawler crawler;
//  @Inject
//  private Profiler profiler;
//  private void run() throws Exception {
//    Guice.createInjector(new WebCrawlerModule(config), new ProfilerModule()).injectMembers(this);
//    CrawlResult result = crawler.crawl(config.getStartPages());
//    CrawlResultWriter resultWriter = new CrawlResultWriter(result);
//    // TODO: Write the crawl results to a JSON file (or System.out if the file name is empty)
//    if (this.config.getResultPath().isEmpty()) {
//      resultWriter.write(new OutputStreamWriter(System.out));
//    }else {
////      Path path = Paths.get(this.config.getResultPath());
////      resultWriter.write(path);
//      resultWriter.write(Path.of(this.config.getResultPath()));
//    }
//    // TODO: Write the profile data to a text file (or System.out if the file name is empty)
//    if (this.config.getProfileOutputPath().isEmpty()) {
//      profiler.writeData(new OutputStreamWriter(System.out));
//    }else {
////      Path path = Paths.get(this.config.getProfileOutputPath());
////      profiler.writeData(path);
//      profiler.writeData(Path.of(this.config.getProfileOutputPath()));
//    }
//  }
//  public static void main(String[] args) throws Exception {
//    if (args.length != 1) {
//      System.out.println("Usage: WebCrawlerMain [starting-url]");
//      return;
//    }
//    CrawlerConfiguration config = new ConfigurationLoader(Path.of(args[0])).load();
//    new WebCrawlerMain(config).run();
//  }
//}
