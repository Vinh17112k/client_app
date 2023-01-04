package com.app.service.client.service.impl;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.config.security.SecurityUtilsClient;
import com.app.service.client.domain.address.AddressDTO;
import com.app.service.client.domain.cancel.CancelDTO;
import com.app.service.client.domain.order.OrderCancelDTO;
import com.app.service.client.domain.order.OrderRequestDTO;
import com.app.service.client.domain.order.OrderResponseDTO;
import com.app.service.client.domain.order_detail.OrderDetailDTO;
import com.app.service.client.domain.payment.PayPalResponseDTO;
import com.app.service.client.domain.product.ProductDTO;
import com.app.service.client.mapper.*;
import com.app.service.client.model.*;
import com.app.service.client.repository.*;
import com.app.service.client.service.MessageService;
import com.app.service.client.service.OrderService;
import com.app.service.client.service.PayPalService;
import com.app.service.client.utils.DataUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderCancelRepository orderCancelRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WardRepository wardRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CusMapper cusMapper;
    @Autowired
    private ProductClientMapper productClientMapper;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private CancelOrderClientMapper cancelOrderClientMapper;
    @Autowired
    private OrderDetailMapperClient orderDetailMapperClient;
    @Autowired
    private OrderMapperClient orderMapperClient;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private JavaMailSender sender;

    @Autowired
    VoucherProductRepository voucherProductRepository;

    @Autowired
    private Configuration config;

    @Override
    public PayPalResponseDTO save(OrderRequestDTO orderRequestDTO) throws ValidateException {
        validateOrder(orderRequestDTO);
        Cart cart = cartRepository.findById(orderRequestDTO.getCartId()).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.code.cart.notfound")));
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryDate(LocalDateTime.now());
        order.setSubTotal(cart.getSubTotal());
        order.setShippingTotal(orderRequestDTO.getTotal());
        order.setTaxRate(cart.getTaxRate());
        order.setTaxTotal(cart.getTaxTotal());
        order.setGrandTotal(cart.getGrandTotal());
        order.setPaymentId(orderRequestDTO.getPaymentId());
        order.setShipmentId(orderRequestDTO.getShipmentId());
//        order.setOrderStatus("Pending");
        order.setDescription(cart.getDescription());

        //order details
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItemList()) {
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setOurPrice(cartItem.getOurPrice());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setTotalPrice(cartItem.getTotalPrice());
            orderDetail.setIsDeleted(false);
            orderDetailList.add(orderDetail);

            log.debug("log to notification");
            Notification notification = new Notification();
            //1 is user
            notification.setType(1);
            notification.setProductId(cartItem.getProduct().getId());
            notification.setCustomerId(SecurityUtilsClient.getCustomerId());
            notification.setContent(messageService.getMessage("error.notification.order",
                SecurityUtilsClient.getUsername(), cartItem.getProduct().getName()));
            notificationRepository.save(notification);
        }
        //set order details in list
        order.setOrderDetailList(orderDetailList);
        emptyCart(order.getCustomer());
        orderRepository.save(order);
        sendMailOrder(order);
        //return paypal
        if (orderRequestDTO.getPaymentId() == 2) {
            return payPalService.createPayment(
                (double) Math.round((order.getGrandTotal() + order.getShippingTotal()) / 2400000));
        } else {
            return new PayPalResponseDTO(null, false);
        }
    }

    private void emptyCart(Customer customer) throws ValidateException {
        Long cartId = customer.getShoppingCart().getId();
        Cart cart = cartRepository.findById(cartId).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.code.cart.notfound")));
        cart.setIsDeleted(Boolean.TRUE);
        List<CartItem> cartItemList = cartItemRepository.findByCartId(cartId);
        cartItemList.forEach(ci -> {
            ci.setIsDeleted(Boolean.TRUE);
            ci.setIsActive(0);
        });
        cartRepository.save(cart);
        cartItemList.forEach(ci -> cartItemRepository.save(ci));
    }

    private void sendMailOrder(Order order) {
        MimeMessage message = sender.createMimeMessage();
        List<Product> products = productRepository.getProducts(order.getOrderId());
        District district = new District();
        Province province = new Province();
        Ward ward = new Ward();
        String wardName = "";
        String districtName = "";
        String provinceName = "";
        if (DataUtils.isNull(SecurityUtilsClient.getCustomer().getWardCode())) {
            wardName = "";
            districtName = "";
            provinceName = "";
        } else {
            ward = wardRepository.getWardByCode(SecurityUtilsClient.getCustomer().getWardCode());
            wardName = ward.getWardName();
            district = districtRepository.findById(
                wardRepository.getWardByCode(SecurityUtilsClient.getCustomer().getWardCode())
                    .getDistrictID()).get();
            province = provinceRepository.findById(districtRepository.findById(
                wardRepository.getWardByCode(SecurityUtilsClient.getCustomer().getWardCode())
                    .getDistrictID()).get().getProvinceID()).get();
            districtName = district.getDistrictName();
            provinceName = province.getProvinceName();
        }
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setWardName(wardName);
        addressDTO.setDistrictName(districtName);
        addressDTO.setProvinceName(provinceName);

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrder(order);
        orderDetailDTO.setAddressDTO(addressDTO);
        orderDetailDTO.setCustomerDTO(cusMapper.toDto(order.getCustomer()));
        orderDetailDTO.setProductDTOS(productClientMapper.toDto(products));
        if (!DataUtils.isNull(order.getPaymentId())) {
            orderDetailDTO.setPayment(paymentRepository.findById(order.getPaymentId()).get());
        }
        if (!DataUtils.isNull(order.getShipmentId())) {
            orderDetailDTO.setShipment(shipmentRepository.findById(order.getPaymentId()).get());
        }
        Map<String, Object> model = new HashMap<>();
        model.put("orderDetailDTO", orderDetailDTO);
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
            // add attachment

            Template t = config.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(order.getCustomer().getEmail());
            helper.setText(html, true);
            helper.setSubject(DataUtils.EMAIL_SUBJECT_ORDER);
            helper.setFrom(DataUtils.EMAIL_SENDER);
            sender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            e.getMessage();
        }
    }

    @Override
    public OrderDetailDTO orderDetailDTO(Long id) throws ValidateException {
        //get customer
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ValidateException("error.code.order.notfound"));
        Customer customer = customerRepository.findById(order.getCustomer().getCustomerId())
            .orElseThrow(() -> new ValidateException("error.user.notfound"));
        List<Product> products = productRepository.getProducts(id);
        District district = new District();
        Province province = new Province();
        Ward ward = new Ward();
        String wardName = "";
        String districtName = "";
        String provinceName = "";
        if (DataUtils.isNull(customer.getWardCode())) {
            wardName = "";
            districtName = "";
            provinceName = "";
        } else {
            ward = wardRepository.getWardByCode(customer.getWardCode());
            wardName = ward.getWardName();
            district = districtRepository.findById(
                wardRepository.getWardByCode(customer.getWardCode()).getDistrictID()).get();
            province = provinceRepository.findById(districtRepository.findById(
                    wardRepository.getWardByCode(customer.getWardCode()).getDistrictID()).get()
                .getProvinceID()).get();
            districtName = district.getDistrictName();
            provinceName = province.getProvinceName();
        }
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setWardName(wardName);
        addressDTO.setDistrictName(districtName);
        addressDTO.setProvinceName(provinceName);

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrder(order);
        orderDetailDTO.setAddressDTO(addressDTO);
        orderDetailDTO.setCustomerDTO(cusMapper.toDto(customer));
        orderDetailDTO.setProductDTOS(productClientMapper.toDto(products));
        for (ProductDTO productDTO : orderDetailDTO.getProductDTOS()) {
            productDTO.setTotalPrice(
                orderDetailRepository.getOrderDetailsByOrderIdAndProduct(order.getOrderId(),
                    productDTO.getId()).getTotalPrice());
            productDTO.setTotalQuantity(
                orderDetailRepository.getOrderDetailsByOrderIdAndProduct(order.getOrderId(),
                    productDTO.getId()).getQuantity());
        }
        if (!DataUtils.isNull(order.getPaymentId())) {
            orderDetailDTO.setPayment(paymentRepository.findById(order.getPaymentId()).get());
        }
        if (!DataUtils.isNull(order.getShipmentId())) {
            orderDetailDTO.setShipment(shipmentRepository.findById(order.getPaymentId()).get());
        }
        return orderDetailDTO;
    }

    @Override
    public Boolean cancelOrder(CancelDTO cancelDTO) throws ValidateException {
        Order order = orderRepository.findById(cancelDTO.getOrderId()).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.code.order.notfound")));
        List<Long> productId = orderRepository.findIdFromOrderDetail(order.getOrderId());
        order.setStatus(2);  //da huy
        order.setIsDeleted(Boolean.TRUE);  //da huy
        OrderCancel orderCancel = new OrderCancel();
        orderCancel.setOrderId(cancelDTO.getOrderId());
        orderCancel.setCustomerId(SecurityUtilsClient.getCustomerId());
        orderCancel.setReason(cancelDTO.getReason());
        //2 là người mua huy
        orderCancel.setType(2);
        List<OrderDetail> orderDetailList = orderDetailRepository.getOrderDetailsByOrderId(
            cancelDTO.getOrderId());
        orderDetailList.forEach(od -> od.setIsDeleted(Boolean.TRUE));
        orderCancelRepository.save(orderCancel);
        log.debug("log to notificaton");
        productId.forEach(p -> {
            Notification notification = new Notification();
            notification.setType(1);
            notification.setProductId(p);
            notification.setCustomerId(SecurityUtilsClient.getCustomerId());
            notification.setContent(messageService.getMessage("error.notification.cancelorder",
                SecurityUtilsClient.getUsername(), productRepository.findById(p).get().getName()));
            notificationRepository.save(notification);
        });

        return Boolean.TRUE;
    }

    @Override
    public List<OrderResponseDTO> listOrder(Long customerId) throws ValidateException {
        if (SecurityUtilsClient.getCustomerId() != customerId) {
            throw new ValidateException(messageService.getMessage("error.user.notfound"));
        }
        List<Order> orders = orderRepository.listOrder(customerId);
        List<OrderResponseDTO> orderRequestDTOS = new ArrayList<>();
        orders.forEach(o -> {
            OrderResponseDTO orderResponseDTO = orderMapperClient.toDto(o);
            orderResponseDTO.setPayment(paymentRepository.findById(o.getPaymentId()).get());
            orderResponseDTO.setShipment(shipmentRepository.findById(o.getPaymentId()).get());
            orderRequestDTOS.add(orderResponseDTO);
        });
        return orderRequestDTOS;
    }

    @Override
    public List<OrderCancelDTO> listOrderDetailCancel(Long customerId) throws ValidateException {
        if (SecurityUtilsClient.getCustomerId() != customerId) {
            throw new ValidateException(messageService.getMessage("error.user.notfound"));
        }
        List<OrderCancelDTO> orderCancelDTOS = new ArrayList<>();
        List<OrderCancel> orderCancels = orderCancelRepository.getOrderCancels(customerId);
        orderCancels.forEach(oc -> {
            List<OrderDetail> orderDetailList = orderDetailRepository.getOrderDetailsCancelByOrderId(
                oc.getOrderId());
            orderDetailList.forEach(od -> {
                Product p = productRepository.getProduct(oc.getOrderId(), od.getOrderDetailId());
                OrderCancelDTO orderCancelDTO = new OrderCancelDTO();
                orderCancelDTO = cancelOrderClientMapper.EntityToDto(oc);
                orderCancelDTOS.add(orderCancelDTO);
            });
        });
        return orderCancelDTOS;
    }

    private void validateOrder(OrderRequestDTO orderRequestDTO) throws ValidateException {
        if (DataUtils.isNull(orderRequestDTO.getCustomerId())) {
            throw new ValidateException(messageService.getMessage("error.code.customerId.empty"));
        }
        if (DataUtils.isNull(orderRequestDTO.getCartId())) {
            throw new ValidateException(messageService.getMessage("error.code.cartId.empty"));
        }
        if (DataUtils.isNull(orderRequestDTO.getPaymentId())) {
            throw new ValidateException(messageService.getMessage("error.code.paymentId.empty"));
        }
        if (DataUtils.isNull(orderRequestDTO.getShipmentId())) {
            throw new ValidateException(messageService.getMessage("error.code.shipmentId.empty"));
        }
        if (SecurityUtilsClient.getCustomerId() != orderRequestDTO.getCustomerId()) {
            throw new ValidateException(messageService.getMessage("error.user.not.login"));
        }
//        free ship
        if (orderRequestDTO.getShipmentId() == 2) {
            boolean applyFreeShip = false;
            List<Long> productIds = new ArrayList<>();
            cartItemRepository.findByCartId(orderRequestDTO.getCartId())
                .forEach(c -> productIds.add(c.getProduct().getId()));
            for (Long productId : productIds
            ) {
                if (voucherProductRepository.existsVoucherProductByProductIdAndVoucher(productId)) {
                    applyFreeShip = true;
                    break;
                }
            }
            if (!applyFreeShip) {
                throw new ValidateException(messageService.getMessage("error.code.paypal.empty"));
            }
        }
    }
}