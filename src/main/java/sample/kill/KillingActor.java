package sample.kill;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import sample.exception.ExceptionThrowingActor;

public class KillingActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() {
        final ActorRef victim = getContext().actorOf(Props.create(VictimActor.class), "victim");
        victim.tell(VictimActor.Msg.GREET, getSelf());
        getContext().watch(victim);
    }

    @Override
    public void onReceive(Object msg) {
        if (msg == VictimActor.Msg.KILL_ME) {
            getSender().tell(akka.actor.Kill.getInstance(), ActorRef.noSender());
        } else if (msg instanceof Terminated) {
            log.info("Received Terminated msg for " + ((Terminated) msg).getActor().path());
            log.info("Stopping self() now");
            getContext().stop(self());
        } else {
            unhandled(msg);
        }
    }

}
