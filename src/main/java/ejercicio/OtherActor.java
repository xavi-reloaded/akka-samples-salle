package ejercicio;

import akka.actor.UntypedActor;
import akka.japi.Procedure;

public class OtherActor extends UntypedActor {



    @Override
    public void onReceive(Object msg) {


        if (msg instanceof Integer) {
            System.out.println("inside other actor number:"+msg);

            // HAY UNA TRAMPA AQUÍ ;)

            getSelf().tell(new myTypeDTO((Integer)msg),self());
        } else {
            unhandled(msg);
        }
    }

    class myTypeDTO {
        Integer data;
        myTypeDTO(Integer number) {
            data = 5+number;
        }
    }

}
