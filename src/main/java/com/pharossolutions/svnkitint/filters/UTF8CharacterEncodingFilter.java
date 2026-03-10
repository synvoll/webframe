package com.pharossolutions.svnkitint.filters;

import org.springframework.web.filter.CharacterEncodingFilter;

public class UTF8CharacterEncodingFilter extends CharacterEncodingFilter {

  public UTF8CharacterEncodingFilter() {
    setEncoding("UTF-8");
    setForceEncoding(true);
  }
}