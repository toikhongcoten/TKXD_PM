package starter;

import java.util.ArrayList;
import java.util.List;

public class Publisher {

    List<Subscriber> listSubscriber;

    public Publisher() {
        listSubscriber = new ArrayList<>();
    }

    public void addSubscriber(Subscriber subscriber) {
        listSubscriber.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber) {
        listSubscriber.remove(subscriber);
    }

    public void notifySubscriber() {
        for (Subscriber subscriber : listSubscriber) {
            subscriber.update();
        }
    }
}

