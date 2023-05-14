import controller.CollageController;
import controller.CollageControllerImpl;
import model.*;
import org.junit.Test;

import java.awt.Color;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import view.CollageView;
import view.CollageViewImpl;
import static org.junit.Assert.assertEquals;


/**
 * A Test for CollageModelppm.
 */

public class CollageTest {


  @Test(expected = IllegalArgumentException.class)
  public void testBadImage() {
    Image img = new ImageImpl("", null, 20);
  }

  @Test
  public void testImage() {
    Pixel[][] pixel = {{new PixelImpl(new Color(0, 0, 0))}};
    Image img = new ImageImpl("", pixel, 20);
    assertEquals(0, img.getPixel(0, 0).getRed());
  }

  @Test
  public void testgetMaxVal() {
    Pixel[][] pixel = {{new PixelImpl(new Color(0, 0, 0))}};
    Image img = new ImageImpl("", pixel, 20);
    assertEquals(20, img.getMaxValue());
  }

  @Test
  public void testPixelGetMaxRGB() {
    Pixel p = new PixelImpl(new Color(0, 0, 0));
    assertEquals(255, p.getMaxRGB());
  }

  @Test
  public void getName() {
    Pixel[][] pixel = {{new PixelImpl(new Color(0, 0, 0))}};
    Image img = new ImageImpl("bruh", pixel, 20);
    assertEquals("bruh", img.getName());
  }

  @Test
  public void testGetPixel() {
    Pixel[][] pixel = {{new PixelImpl(new Color(255, 100, 50))}};
    Image img = new ImageImpl("", pixel, 20);
    assertEquals(255, img.getPixel(0, 0).getRed());
    assertEquals(100, img.getPixel(0, 0).getGreen());
    assertEquals(50, img.getPixel(0, 0).getBlue());
  }

  @Test
  public void testGetHeight() {
    Pixel[][] pixel = {{new PixelImpl(new Color(0, 0, 0))}};
    Image img = new ImageImpl("", pixel, 20);
    assertEquals(1, img.getHeight());
  }

  @Test
  public void testGetWidth() {
    Pixel[][] pixel = {{new PixelImpl(new Color(0, 0, 0))}};
    Image img = new ImageImpl("", pixel, 20);
    assertEquals(1, img.getWidth());
  }

  @Test
  public void testGetImage() throws FileNotFoundException {
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    Image g = ipm.getImage("g");
    assertEquals(42, g.getPixel(0, 0).getBlue());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadGetImage() {
    CollageModel ipm = new CollageModelImpl();
    ipm.getImage("asdf");
  }

  @Test
  public void testLoad() throws FileNotFoundException {
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    Image g = ipm.getImage("g");
    assertEquals(47, g.getPixel(0, 0).getRed(), 0.1);
  }

  @Test(expected = FileNotFoundException.class)
  public void tesBadLoad() throws FileNotFoundException {
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/bbbb", "bbbb");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadSave() throws IOException {
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    ipm.getImage("g");
    ipm.save("./res/", "zxczxc");
  }

  @Test
  public void testSave() throws IOException {
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    ipm.getImage("g");
    ipm.save("./res/test2.ppm", "g");
    ipm.load("./res/test2.ppm", "t");
    Image t = ipm.getImage("t");
    assertEquals(47, t.getPixel(0, 0).getRed());
  }

  @Test
  public void testRedComp() throws FileNotFoundException {
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    ipm.redComponent("g", "comp");
    Image g = ipm.getImage("comp");
    assertEquals(42, g.getPixel(0, 0).getRed());
  }

  @Test
  public void testGreenComp() throws FileNotFoundException {
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    ipm.greenComponent("g", "comp");
    Image g = ipm.getImage("comp");
    assertEquals(0, g.getPixel(0, 0).getBlue());
  }

  @Test
  public void testBlueComp() throws FileNotFoundException {
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    ipm.blueComponent("g", "comp");
    Image g = ipm.getImage("comp");
    assertEquals(42, g.getPixel(0, 0).getBlue());
  }

  @Test
  public void testPixelGetValue() {
    Pixel[][] pixel = {{new PixelImpl(new Color(255, 100, 50))}};
    Image img = new ImageImpl("", pixel, 20);
    assertEquals(255, img.getPixel(0, 0).getValue());
  }

  @Test
  public void testBrightenIntensity() throws FileNotFoundException {
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    ipm.brightenIntensity("g", "comp");
    Image v = ipm.getImage("comp");
    assertEquals(92, v.getPixel(0, 0).getRed());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadImageView() throws FileNotFoundException {
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    CollageView ipv = new CollageViewImpl(ipm, null);
  }


  @Test
  public void testRenderMessage() throws IOException {
    CollageModel ipm = new CollageModelImpl();
    StringBuilder log = new StringBuilder();
    ipm.load("./res/k.PPM", "g");
    CollageView ipv = new CollageViewImpl(ipm, log);
    ipv.renderMessage("render");
    String output = log.toString();
    assertEquals("render\n", output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadController() throws FileNotFoundException {
    Readable ir = new InputStreamReader(System.in);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageController c = new CollageControllerImpl(null, null, null);
    CollageController c2 = new CollageControllerImpl(ipm, null, null);
    CollageController c3 = new CollageControllerImpl(null, null, ir);
    CollageController c4 = new CollageControllerImpl(null, ipv, null);
  }

  @Test
  public void testMultipleInputs() throws FileNotFoundException {
    String s = "load res/k.PPM g red-component g comp q";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("/res/k.PPM", "g");
    Appendable log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageController c = new CollageControllerImpl(ipm, ipv, ir);
    c.run();
    String output = log.toString();
    String[] tokens = output.split("\n");
    String gc = "g was saved as a red image called comp";
    String img = "Image res/k.PPM was loaded and called g";
    assertEquals(gc, tokens[tokens.length - 2]);
    assertEquals(img, tokens[tokens.length - 4]);
  }

  @Test
  public void testImageProcTextRed() throws IOException {
    String s = "g comp";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.redComponent();
    String output = log.toString();
    assertEquals("g was saved as a red image called comp\n", output);
  }

  @Test
  public void testImageProcTextBlueComp() throws IOException {
    String s = "g comp";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.blueComponent();
    String output = log.toString();
    assertEquals("g was saved as a blue image called comp\n", output);
  }

  @Test
  public void testImageProcTextGreenComp() throws IOException {
    String s = "g comp";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.greenComponent();
    String output = log.toString();
    assertEquals("g was saved as a green image called comp\n", output);
  }

  @Test
  public void testBrightnessValue() throws IOException {
    String s = "g comp";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.brightenValue();
    String output = log.toString();
    String res = "g was saved as a brightened-value image called comp\n";
    assertEquals(res, output);
  }

  @Test
  public void testDarkenValue() throws IOException {
    Readable ir = new StringReader("g comp");
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.darkenValue();
    String output = log.toString();
    String res = "g was saved as a darkened-value image called comp\n";
    assertEquals(res, output);
  }

  @Test
  public void testTextLuma() throws IOException {
    String s = "g bright";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.brightenLuma();
    String output = log.toString();
    String res = "g was saved as a brightened-luma image called bright\n";
    assertEquals(res, output);
  }

  @Test
  public void testTextIntensity() throws IOException {
    String s = "g bright";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.brightenIntensity();
    String output = log.toString();
    String res = "g was saved as a brightened-intensity image called bright\n";
    assertEquals(res, output);
  }

  @Test
  public void testTextValue() throws IOException {
    String s = "g bright";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.brightenValue();
    String output = log.toString();
    String res = "g was saved as a brightened-value image called bright\n";
    assertEquals(res, output);
  }

  @Test
  public void testTextLumaD() throws IOException {
    String s = "g bright";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.darkenLuma();
    String output = log.toString();
    String res = "g was saved as a darkened-luma image called bright\n";
    assertEquals(res, output);
  }

  @Test
  public void testTextIntensityD() throws IOException {
    String s = "g bright";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.darkenIntensity();
    String output = log.toString();
    String res = "g was saved as a darkened-intensity image called bright\n";
    assertEquals(res, output);
  }

  @Test
  public void testTextValueD() throws IOException {
    String s = "g bright";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.darkenValue();
    String output = log.toString();
    String res = "g was saved as a darkened-value image called bright\n";
    assertEquals(res, output);
  }

  @Test
  public void testImageProcTextLoad() throws IOException {
    String s = "./res/k.PPM g";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.load();
    String output = log.toString();
    String res = "Image ./res/k.PPM was loaded and called g\n";
    assertEquals(res, output);
  }

  @Test
  public void testImageProcTextSave() throws IOException {
    String s = "./res/test.ppm g";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.save();
    String output = log.toString();
    assertEquals("g was saved as ./res/test.ppm\n", output);
  }


  @Test
  public void testDarkenMultiply() throws IOException {
    String s = "g bright";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.darkenMultiply();
    String output = log.toString();
    String res = "g was saved as a darkened-multiply image called bright\n";
    assertEquals(res, output);
  }

  @Test
  public void testBrightenScreen() throws IOException {
    String s = "g darken-multiply";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.brightenScreen();
    String output = log.toString();
    String res = "g was saved as a brightened-screen image called bright\n";
    assertEquals(res, output);
  }

  @Test
  public void testDifference() throws IOException {
    String s = "g bright";
    Readable ir = new StringReader(s);
    CollageModel ipm = new CollageModelImpl();
    ipm.load("./res/k.PPM", "g");
    StringBuilder log = new StringBuilder();
    CollageView ipv = new CollageViewImpl(ipm, log);
    CollageControllerImpl c = new CollageControllerImpl(ipm, ipv, ir);
    c.difference();
    String output = log.toString();
    String res = "g was saved as a differenced image called bright\n";
    assertEquals(res, output);
  }
}
