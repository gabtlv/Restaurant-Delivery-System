package com.restaurant.order.business;

import io.kubemq.sdk.event.EventReceive;
import io.kubemq.sdk.event.Subscriber;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;
import io.kubemq.sdk.subscription.EventsStoreType;
import io.kubemq.sdk.tools.Converter;
import io.grpc.stub.StreamObserver;
import javax.net.ssl.SSLException;

public class Messaging {

    public static void Receiving_Events_Store(String cname) throws SSLException, Exception {
        String kubeMQAddress = System.getenv("kubeMQAddress");
        Subscriber subscriber = new Subscriber(kubeMQAddress);
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setChannel(cname);
        subscribeRequest.setClientID("order-receiver");
        subscribeRequest.setSubscribeType(SubscribeType.EventsStore);
        subscribeRequest.setEventsStoreType(EventsStoreType.StartAtSequence);
        subscribeRequest.setEventsStoreTypeValue(1);

        StreamObserver<EventReceive> streamObserver = new StreamObserver<EventReceive>() {
            @Override
            public void onNext(EventReceive value) {
                try {
                    String val = (String) Converter.FromByteArray(value.getBody());
                    String[] msgParts = val.split(":");
                    if (msgParts.length == 3) {
                        if (msgParts[0].equals("ORDER")) {
                            String customerName = msgParts[1];
                            double total = Double.parseDouble(msgParts[2]);
                            com.restaurant.order.persistence.Orders_CRUD.insertOrder(
                                new com.restaurant.order.helper.Order(customerName, total, "Placed")
                            );
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("onError: %s", t.getMessage());
            }

            @Override
            public void onCompleted() {
            }
        };
        subscriber.SubscribeToEvents(subscribeRequest, streamObserver);
    }
}