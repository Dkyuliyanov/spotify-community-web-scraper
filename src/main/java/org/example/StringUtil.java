package org.example;

public class StringUtil {

  public static final String XPATH_FIRST_PART = "/html/body/div[2]/center/div[1]/div/div/div/div[1]/div[6]/div/div/div/div[2]/div[1]/div[";
  public static final String XPATH_SECOND_PART = "]/div/div/div[1]/div[2]/div/div[3]/div/div/div[1]/div";

  public static String generatePostXpath(int postNumber) {

    return XPATH_FIRST_PART + postNumber + XPATH_SECOND_PART;
  }

}
