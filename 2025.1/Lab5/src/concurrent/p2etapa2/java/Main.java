import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Use: java Main <num_producers> <max_items_per_producer> <producing_time> <num_consumers> <consuming_time>");
            return;
        }
        
        int numProducers = Integer.parseInt(args[0]);
        int maxItemsPerProducer = Integer.parseInt(args[1]);
        int producingTime = Integer.parseInt(args[2]);
        int numConsumers = Integer.parseInt(args[3]);
        int consumingTime = Integer.parseInt(args[4]);
        
        Buffer buffer = new Buffer();
        ArrayList<Thread> producersT= new ArrayList<>();
        ArrayList<Thread> consumersT= new ArrayList<>();
        for (int i = 1; i <= numProducers; i++) {
            Producer producer = new Producer(i, buffer, maxItemsPerProducer, producingTime);
            producersT.add(new Thread(producer));
            producersT.get(producersT.size()-1).start();
        }
        
        for (int i = 1; i <= numConsumers; i++) {
            Consumer consumer = new Consumer(i, buffer, consumingTime);
            consumersT.add(new Thread(consumer));
            consumersT.get(consumersT.size()-1).start();
        }

        for (int i = 0; i < numProducers; i++) {
            producersT.get(i).join();
        }

        for (int i = 0; i < numConsumers; i++) {
            consumersT.get(i).join();
        }


    }
}
