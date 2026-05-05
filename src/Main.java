import domain.Order;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        RestaurantOrders ro = RestaurantOrders.read("orders.json");

        System.out.println("===== Задание 1 =====");

        System.out.println("\n-- Все заказы --");
        ro.printOrders();

        System.out.println("\n-- Топ 3 по стоимости --");
        ro.getTopNByTotal(3).forEach(o ->
                System.out.println(o.getCustomer().getFullName() + " | " + o.getTotal()));

        System.out.println("\n-- Нижние 3 по стоимости --");
        ro.getBottomNByTotal(3).forEach(o ->
                System.out.println(o.getCustomer().getFullName() + " | " + o.getTotal()));

        System.out.println("\n-- Заказы на дом --");
        ro.getHomeDeliveryOrders().forEach(o ->
                System.out.println(o.getCustomer().getFullName() + " | " + o.getTotal()));

        System.out.println("\n-- Самый прибыльный заказ на дом --");
        Order most = ro.getMostProfitableHomeDelivery();
        System.out.println(most.getCustomer().getFullName() + " | " + most.getTotal());

        System.out.println("\n-- Наименее прибыльный заказ на дом --");
        Order least = ro.getLeastProfitableHomeDelivery();
        System.out.println(least.getCustomer().getFullName() + " | " + least.getTotal());

        System.out.println("\n-- Заказы от 50 до 200 --");
        ro.getOrdersByTotalRange(50, 200).forEach(o ->
                System.out.println(o.getCustomer().getFullName() + " | " + o.getTotal()));

        System.out.println("\n-- Общая выручка --");
        System.out.println(ro.getTotalRevenue());

        System.out.println("\n-- Уникальные email (отсортированные) --");
        ro.getSortedUniqueEmails().forEach(System.out::println);

        System.out.println("\n===== Задание 2 =====");

        System.out.println("\n-- Заказы по клиентам --");
        ro.getOrdersGroupedByCustomer().forEach((name, orderList) ->
                System.out.println(name + " | заказов: " + orderList.size()));

        System.out.println("\n-- Сумма заказов по клиентам --");
        ro.getTotalByCustomer().forEach((name, total) ->
                System.out.println(name + " | " + total));

        System.out.println("\n-- Клиент с максимальной суммой --");
        System.out.println(ro.getCustomerWithMaxTotal());

        System.out.println("\n-- Клиент с минимальной суммой --");
        System.out.println(ro.getCustomerWithMinTotal());

        System.out.println("\n-- Количество проданных товаров --");
        ro.getSoldAmountByItem().forEach((name, amount) ->
                System.out.println(name + " | " + amount));

        System.out.println("\n===== Задание 3 =====");

        System.out.println("\n-- Email клиентов, заказывавших 'Pizza' --");
        ro.getEmailsByItemName("Pizza").forEach(System.out::println);
    }
}