package sample.kill;

public class Main {

  public static void main(String[] args) {

    akka.Main.main(new String[] { KillingActor.class.getName() });
  }
}
