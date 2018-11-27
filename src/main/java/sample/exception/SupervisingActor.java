package sample.exception;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;

public class SupervisingActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);


    @Override
    public void preStart() {
        // create the greeter actor
        final ActorRef victim = getContext().actorOf(Props.create(ExceptionThrowingActor.class), "exception_thrower");
        getContext().watch(victim);
        victim.tell(ExceptionThrowingActor.Msg.THROW_RESUMABLE_EXCEPTION, getSelf());
        victim.tell(ExceptionThrowingActor.Msg.THROW_RESTARTABLE_EXCEPTION, getSelf());  // Note: Send MORE than 3 of these per min and the child will be stopped anyway
        victim.tell(ExceptionThrowingActor.Msg.THROW_RESTARTABLE_EXCEPTION, getSelf());  // Note: Send MORE than 3 of these per min and the child will be stopped anyway
        victim.tell(ExceptionThrowingActor.Msg.THROW_RESTARTABLE_EXCEPTION, getSelf());  // Note: Send MORE than 3 of these per min and the child will be stopped anyway
        victim.tell(ExceptionThrowingActor.Msg.THROW_FATAL_EXCEPTION, getSelf());
    }

    @Override
    public void onReceive(Object msg) {
        if (msg instanceof Terminated) {
            log.info("Terminated msg for " + ((Terminated) msg).getActor().path());
            getContext().stop(self());
        } else {
            unhandled(msg);
        }
    }

    private SupervisorStrategy strategy = new OneForOneStrategy(3, Duration.create("1 minute"),
            throwable -> {
                if (throwable instanceof ResumableException) {
                    log.warning("Got a ResumableException: {}", throwable);
                    log.warning("Will RESUME child actor");
                    return resume();
                } else if (throwable instanceof RestartableException) {
                    log.warning("Got a RestartableException: {}", throwable);
                    log.warning("Will RESTART child actor");
                    return restart();
                } else if (throwable instanceof FatalException) {
                    log.warning("Got a FatalException: {}", throwable);
                    log.warning("Will STOP child actor");
                    return stop();
                } else {
                    log.warning("Escalating unhandled exception: {}", throwable);
                    return escalate();
                }
            }, false);

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }


}
