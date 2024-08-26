package com.bootcamp.project.project_yahoo_finance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bootcamp.project.project_yahoo_finance.service.DataBaseService;

@Component
public class AppRunner implements CommandLineRunner {
  
  @Autowired
  DataBaseService dataBaseService;

  @Override
  public void run(String... args){
    dataBaseService.initialize();
  }
}
