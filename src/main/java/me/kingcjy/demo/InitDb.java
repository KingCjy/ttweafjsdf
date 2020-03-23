package me.kingcjy.demo;

import lombok.RequiredArgsConstructor;
import me.kingcjy.demo.domain.*;
import me.kingcjy.demo.domain.item.Book;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager entityManager;

        public void dbInit1() {
            Member member = createMember("userA", "서울", "1", "1111");
            entityManager.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            entityManager.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 10000, 100);
            entityManager.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 10000, 2);


            Delivery delivery = createDelivery(member);

            Order order =  Order.createOrder(member, delivery, orderItem1, orderItem2);

            entityManager.persist(order);
        }

        public void dbInit2() {

            Member member = createMember("userB", "부산", "2", "2222");
            entityManager.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            entityManager.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            entityManager.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);


            Delivery delivery = createDelivery(member);

            Order order =  Order.createOrder(member, delivery, orderItem1, orderItem2);

            entityManager.persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Member createMember(String name, String city, String street, String zipCode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipCode));
            return member;
        }
    }
}
