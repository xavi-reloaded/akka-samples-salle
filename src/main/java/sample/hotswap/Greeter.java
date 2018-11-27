package sample.hotswap;

import akka.actor.UntypedActor;
import akka.japi.Procedure;

public class Greeter extends UntypedActor {

    public static enum Msg {
        GREET, DONE
    }

    @Override
    public void onReceive(Object msg) {
        System.out.println("Got message " + msg.getClass());
        if (msg == Msg.GREET) {
            System.out.println("Hello World!");
            getContext().become(readyToLeave);
        } else {
            unhandled(msg);
        }
    }


    Procedure<Object> readyToLeave = msg -> {
        System.out.println("Got message " + msg.getClass());
        if (msg == Msg.GREET) {
            System.out.println("See you in a bit World!");
            getSender().tell(Msg.DONE, getSelf());
        }
    };
}
