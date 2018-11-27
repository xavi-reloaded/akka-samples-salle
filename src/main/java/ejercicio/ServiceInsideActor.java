package ejercicio;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import ejercicio.OtherActor.myTypeDTO;

public class ServiceInsideActor extends UntypedActor {

    SimpleSampleService service;
    ActorRef otherActor;

    public ServiceInsideActor() {
        service = new SimpleSampleService();

    }

    @Override
    public void preStart() {
        // create the greeter actor
        otherActor = getContext().actorOf(Props.create(OtherActor.class), "other");
        otherActor.tell(service.returnANumber(), getSelf());

    }

    @Override
    public void onReceive(Object msg) {

        System.out.println("here I am");
        if (msg instanceof myTypeDTO) {
            System.out.println("passed Number" + ((myTypeDTO) msg).data);
        }
    }


}
