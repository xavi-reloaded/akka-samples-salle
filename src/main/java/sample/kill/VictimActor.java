package sample.kill;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class VictimActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static enum Msg {
        GREET, KILL_ME
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg == Msg.GREET) {
            System.out.println("Hello World! Asking to be killed now");
            getSender().tell(Msg.KILL_ME, getSelf());
        } else {
            unhandled(msg);
        }
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        log.info("Victim actor stopped");
    }
}
