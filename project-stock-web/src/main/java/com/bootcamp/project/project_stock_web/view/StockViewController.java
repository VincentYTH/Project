package com.bootcamp.project.project_stock_web.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StockViewController {

  @GetMapping("/stock-chart")
  public String test(Model model) {
    return "StockChart";
  }

}
