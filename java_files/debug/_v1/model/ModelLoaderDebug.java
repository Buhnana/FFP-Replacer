package mcheli.debug._v1.model;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ModelLoaderDebug {
  public static void main(String[] args) {
    try {
      ObjModel model = ObjParser.parse(new FileInputStream(new File(getResource("models/gltd.obj"))));
      model.printInfo();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  static URI getResource(String path) {
    URL url = ModelLoaderDebug.class.getClassLoader().getResource("assets/mcheli/" + path);
    try {
      return (url != null) ? url.toURI() : null;
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return null;
    } 
  }
}
