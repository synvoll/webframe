package com.pharossolutions.svnkitint;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(2)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected void customizeRegistration(Dynamic registration) {
    registration.setMultipartConfig(multipartConfigElement());
  }

  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    factory.setMaxFileSize("16MB");
    factory.setMaxRequestSize("16MB");
    return factory.createMultipartConfig();
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] { SecurityConfig.class, AppConfig.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[0];
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }
}
