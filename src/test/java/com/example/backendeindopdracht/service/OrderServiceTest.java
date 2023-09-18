package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.DTO.inputDTO.OrderInputDTO;
import com.example.backendeindopdracht.DTO.inputDTO.UserInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderOutputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.UserOutputDTO;
import com.example.backendeindopdracht.model.Invoice;
import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.ProductRepository;
import com.example.backendeindopdracht.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
OrderRepository orderRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
OrderService orderService;

    @Mock
    UserService userService;





    @Test
    void shouldAddOrder(){

        //arrange
       // Invoice invoice = new Invoice();
       // List <OrderLine> orderLine = new ArrayList<>();
        BigDecimal bd = new BigDecimal(987654321);
        UserInputDTO userInputDTO = new UserInputDTO(2L,"Lizzy", "Telford", "lizzytelford", "lizzytelford@hotmail.com", 1L);
        UserOutputDTO user = userService.addUser(userInputDTO);
        OrderInputDTO orderInputDTO = new OrderInputDTO();
        //orderInputDTO.setInvoice(invoice);
       // orderInputDTO.setOrderLines(orderLine);
        orderInputDTO.setTotalAmount(bd);
        orderInputDTO.setUserId(user.getId());
        orderInputDTO.setId(1L);


        //act
        OrderOutputDTO result = orderService.addOrder(orderInputDTO);


        //assert

        assertEquals( bd,result.getTotalAmount());



    }

    @Test
    void shouldRetrieveAllOrders() {

        //arrange (klaarzetten testdata)
        Invoice invoice1 = new Invoice();
        List <OrderLine> orderLine1 = new ArrayList<>();
        BigDecimal a = new BigDecimal("123456");
        User user1 = new User();
        Order order1 = new Order(null,invoice1, orderLine1, " customername", a, user1, 1L);
        orderRepository.save(order1);
        //act
        List <OrderOutputDTO> result = orderService.getAllOrders();

        //assert
        assertEquals(order1, result);


    }
}