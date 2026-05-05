import com.google.gson.Gson;
import domain.Item;
import domain.Order;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RestaurantOrders {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private List<Order> orders;

    private RestaurantOrders(String fileName) {
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            orders = List.of(gson.fromJson(Files.readString(filePath), Order[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RestaurantOrders read(String fileName) {
        var ro = new RestaurantOrders(fileName);
        ro.getOrders().forEach(Order::calculateTotal);
        return ro;
    }

    public List<Order> getOrders() {
        return orders;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    // Наполните этот класс решением домашнего задания.
    // Вам необходимо создать все необходимые методы
    // для решения заданий из домашки :)
    // вы можете добавлять все необходимые imports
    //


    public void printOrders() {
        orders.forEach(o -> System.out.println(
                o.getCustomer().getFullName() + " | total: " + o.getTotal()
        ));
    }

    public List<Order> getTopNByTotal(int n) {
        return orders
                .stream()
                .sorted(Comparator.comparingDouble(Order::getTotal).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Order> getBottomNByTotal(int n) {
        return orders
                .stream()
                .sorted(Comparator.comparingDouble(Order::getTotal))
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Order> getHomeDeliveryOrders() {
        return orders
                .stream()
                .filter(Order::isHomeDelivery)
                .collect(Collectors.toList());
    }

    public Order getMostProfitableHomeDelivery() {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .reduce((a, b) -> a.getTotal() >= b.getTotal() ? a : b)
                .orElse(null);
    }

    public Order getLeastProfitableHomeDelivery() {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .reduce((a, b) -> a.getTotal() <= b.getTotal() ? a : b)
                .orElse(null);
    }

    public List<Order> getOrdersByTotalRange(double minOrderTotal, double maxOrderTotal) {
        return orders.stream()
                .filter(o -> o.getTotal() > minOrderTotal && o.getTotal() < maxOrderTotal)
                .collect(Collectors.toList());
    }

    public double getTotalRevenue() {
        return orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    public List<String> getSortedUniqueEmails() {
        return orders
                .stream()
                .map(o -> o.getCustomer().getEmail())
                .collect(Collectors.toCollection(TreeSet::new))
                .stream()
                .collect(Collectors.toList());
    }


    public Map<String, List<Order>> getOrdersGroupedByCustomer() {
        return orders.stream()
                .collect(Collectors.groupingBy(o -> o.getCustomer().getFullName()));
    }


    public Map<String, Double> getTotalByCustomer() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getCustomer().getFullName(),
                        Collectors.summingDouble(Order::getTotal)
                ));
    }


    public String getCustomerWithMaxTotal() {
        return getTotalByCustomer().entrySet().stream()
                .reduce((a, b) -> a.getValue() >= b.getValue() ? a : b)
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    public String getCustomerWithMinTotal() {
        return getTotalByCustomer().entrySet().stream()
                .reduce((a, b) -> a.getValue() <= b.getValue() ? a : b)
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    public Map<String, Integer> getSoldAmountByItem() {
        return orders.stream()
                .flatMap(o -> o.getItems().stream())
                .collect(Collectors.groupingBy(
                        Item::getName,
                        Collectors.summingInt(Item::getAmount)
                ));
    }


}
