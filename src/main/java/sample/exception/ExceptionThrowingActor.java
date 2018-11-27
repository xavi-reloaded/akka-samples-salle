package sample.exception;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ExceptionThrowingActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static enum Msg {
        THROW_RESUMABLE_EXCEPTION, THROW_RESTARTABLE_EXCEPTION, THROW_FATAL_EXCEPTION
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg == Msg.THROW_RESUMABLE_EXCEPTION) {
            throw new ResumableException("Oops - resumable");
        } else if (msg == Msg.THROW_RESTARTABLE_EXCEPTION) {
            throw new RestartableException("Oops - restartable");
        } else if (msg == Msg.THROW_FATAL_EXCEPTION) {
            throw new FatalException("Oops - fatal");
        } else {
            unhandled(msg);
        }
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        super.postRestart(reason);
        log.info("Was restarted after " + reason);
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        log.info("ExceptionThrowingActor was stopped");
    }
}
