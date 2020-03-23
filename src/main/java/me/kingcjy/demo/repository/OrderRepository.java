package me.kingcjy.demo.repository;

import lombok.RequiredArgsConstructor;
import me.kingcjy.demo.api.OrderSimpleApiController;
import me.kingcjy.demo.api.OrderSimpleQueryDto;
import me.kingcjy.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;

    public List<Order> findAllWithMemberDelivery() {
        return entityManager.createQuery("select o from Order o join fetch o.member join fetch o.delivery").getResultList();
    }


    public List<OrderSimpleQueryDto> findOrderDtos() {
        return entityManager.createQuery("select new me.kingcjy.demo.api.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderSimpleQueryDto.class).getResultList();
    }

    public List<Order> findAll() {
        return entityManager.createQuery("select o from Order o").getResultList();
    }

    public List<Order> findAllWithItem() {
        return entityManager.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.item i", Order.class
        )
                .setFirstResult(0)
                .setMaxResults(100).getResultList();
    }
}
