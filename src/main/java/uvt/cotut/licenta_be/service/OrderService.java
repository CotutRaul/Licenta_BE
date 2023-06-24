package uvt.cotut.licenta_be.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.exception.ApplicationBusinessException;
import uvt.cotut.licenta_be.exception.ErrorCode;
import uvt.cotut.licenta_be.model.*;
import uvt.cotut.licenta_be.notification.service.EmailSenderService;
import uvt.cotut.licenta_be.repository.OrderRepository;
import uvt.cotut.licenta_be.repository.ProductRepository;
import uvt.cotut.licenta_be.security.SecurityHelper;
import uvt.cotut.licenta_be.service.api.OrderMapper;
import uvt.cotut.licenta_be.service.api.dto.CartDTO;
import uvt.cotut.licenta_be.service.api.dto.DateRangeDTO;
import uvt.cotut.licenta_be.util.ProductSalesStats;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    private final EmailSenderService emailSenderService;

    public Order addOrder(List<CartDTO> cartDTOS, Address address) {
        User userDetails = SecurityHelper.getUserDetails();
        if (userDetails == null) {
            throw new ApplicationBusinessException(ErrorCode.USER_NOT_FOUND);
        }
        Order newOrder = new Order();
        newOrder.setUser(userDetails);
        newOrder.setAddress(address);
        newOrder.setOrderDate(LocalDateTime.now());

        List<OrderAmount> orderAmounts = new ArrayList<>();
        for (CartDTO cartDTO : cartDTOS) {
            Product product = productRepository.findById(cartDTO.getProductId())
                    .orElseThrow(() -> new ApplicationBusinessException(ErrorCode.PRODUCT_NOT_FOUND));

            OrderAmount orderAmount = new OrderAmount();
            orderAmount.setProduct(product);
            orderAmount.setPrice(product.getPrice());
            orderAmount.setOriginalPrice(product.getOriginalPrice());
            orderAmount.setAmount(cartDTO.getAmount());

            orderAmounts.add(orderAmount);
        }

        newOrder.setOrderAmountList(orderAmounts);
        newOrder.setStatus(OrderStatus.ORDERED);
        orderMapper.handleOrderRelations(newOrder);

        Order save = orderRepository.save(newOrder);

        emailSenderService.sendConfirmationEmail(save);
        return save;
    }

    public Order updateOrder(Long id, OrderStatus action) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException(ErrorCode.ORDER_NOT_FOUND));
        User userDetails = SecurityHelper.getUserDetails();

        if (!((userDetails.getId() == order.getUser().getId() && action == OrderStatus.CANCELED) || userDetails.getRole() == Role.ADMIN)) {
            throw new ApplicationBusinessException(ErrorCode.ACCESS_DENIED);
        }


        if (action == OrderStatus.DELIVERED) {
            order.setDeliveredDate(LocalDateTime.now());
        }
        order.setStatus(action);

        Order save = orderRepository.save(order);
        switch (save.getStatus()) {
            case IN_DELIVERY -> emailSenderService.sendInDelivery(save);
            case DELIVERED -> emailSenderService.sendDelivered(save);
            case CANCELED -> emailSenderService.sendCanceled(save);
        }

        return save;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ApplicationBusinessException(ErrorCode.ORDER_NOT_FOUND));
        User userDetails = SecurityHelper.getUserDetails();

        if (!(userDetails.getId() == order.getUser().getId() || userDetails.getRole() == Role.ADMIN)) {
            throw new ApplicationBusinessException(ErrorCode.ACCESS_DENIED);
        }

        return order;
    }

    public List<Order> getAllOrdersByClient() {
        User userDetails = SecurityHelper.getUserDetails();

        return orderRepository.findAllByUser(userDetails);
    }

    public ResponseEntity<byte[]> downloadExcelFile(DateRangeDTO dateRangeDTO) {
        List<Order> orders = orderRepository.findByOrderDateBetween(dateRangeDTO.getDates()[0], dateRangeDTO.getDates()[1]);

        try (Workbook workbook = new XSSFWorkbook()) {

            addContentToExcel(workbook, orders);

            // Generate the Excel file in memory
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "report.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    private void addContentToExcel(Workbook workbook, List<Order> orders) {
        addSheetAllOrders(workbook, orders);
        addSheetProducts(workbook, orders);
    }


    private void addSheetAllOrders(Workbook workbook, List<Order> orders) {
        Sheet sheet = workbook.createSheet("All Orders");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        Row headerRow = sheet.createRow(0);
        int cellPoz = 0;

        String headersAllOrdersSheet = "ID;Client name;Country;City;Street line;Phone number;Order Date;Delivered Date;Price;Status";
        for (String header : headersAllOrdersSheet.split(";")) {
            headerRow.createCell(cellPoz++).setCellValue(header);
        }

        int rowNum = 1;
        for (Order order : orders) {
            Row row = sheet.createRow(rowNum++);
            cellPoz = 0;
            row.createCell(cellPoz++).setCellValue(order.getId());
            row.createCell(cellPoz++).setCellValue(order.getUser().getSurname() + " " + order.getUser().getName());
            row.createCell(cellPoz++).setCellValue(order.getAddress().getCountry());
            row.createCell(cellPoz++).setCellValue(order.getAddress().getCity());
            row.createCell(cellPoz++).setCellValue(order.getAddress().getStreetLine());
            row.createCell(cellPoz++).setCellValue(order.getAddress().getPhoneNumber());
            row.createCell(cellPoz++).setCellValue(order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            row.createCell(cellPoz++).setCellValue(order.getDeliveredDate() != null ?
                    order.getDeliveredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "");
            row.createCell(cellPoz++).setCellValue(decimalFormat.format(order.getOrderAmountList().stream()
                    .map(OrderAmount::getPrice).reduce(0f, Float::sum)));
            row.createCell(cellPoz++).setCellValue(order.getStatus().name());
        }

        // Auto-size columns
        int columnCount = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }

        // Add filter to the header row
        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:J1"));

    }

    private void addSheetProducts(Workbook workbook, List<Order> orders) {
        Sheet sheet = workbook.createSheet("Products sales");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        Map<Product, ProductSalesStats> salesStatsMap = new HashMap<>();

        for (Order order : orders) {
            for (OrderAmount orderAmount : order.getOrderAmountList()) {
                Product product = orderAmount.getProduct();
                ProductSalesStats salesStats = salesStatsMap.getOrDefault(product, new ProductSalesStats());

                salesStats.setUnitsSold(salesStats.getUnitsSold() + orderAmount.getAmount());
                if (orderAmount.getOriginalPrice() != null) {
                    salesStats.setUnitsDiscounted(salesStats.getUnitsDiscounted() + orderAmount.getAmount());
                    salesStats.setTotalPrice(salesStats.getTotalPrice() + (orderAmount.getOriginalPrice() * orderAmount.getAmount()));
                } else {
                    salesStats.setTotalPrice(salesStats.getTotalPrice() + (orderAmount.getPrice() * orderAmount.getAmount()));
                }
                salesStats.setTotalPriceDiscounts(salesStats.getTotalPriceDiscounts() + (orderAmount.getPrice() * orderAmount.getAmount()));

                salesStatsMap.put(product, salesStats);
            }
        }

        Row headerRow = sheet.createRow(0);
        int cellPoz = 0;

        String headersAllOrdersSheet = "ID;Product name;Category;Subcategory;Units sold;Units discounted;Total price;Total price with discounts";
        for (String header : headersAllOrdersSheet.split(";")) {
            headerRow.createCell(cellPoz++).setCellValue(header);
        }

        int rowNum = 1;
        for (Map.Entry<Product, ProductSalesStats> statsEntry : salesStatsMap.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            cellPoz = 0;
            row.createCell(cellPoz++).setCellValue(statsEntry.getKey().getId());
            row.createCell(cellPoz++).setCellValue(statsEntry.getKey().getName());
            row.createCell(cellPoz++).setCellValue(statsEntry.getKey().getSubCategory().getCategory().getName());
            row.createCell(cellPoz++).setCellValue(statsEntry.getKey().getSubCategory().getName());
            row.createCell(cellPoz++).setCellValue(statsEntry.getValue().getUnitsSold());
            row.createCell(cellPoz++).setCellValue(statsEntry.getValue().getUnitsDiscounted());
            row.createCell(cellPoz++).setCellValue(decimalFormat.format((statsEntry.getValue().getTotalPrice())));
            row.createCell(cellPoz++).setCellValue(decimalFormat.format((statsEntry.getValue().getTotalPriceDiscounts())));
        }

        // Auto-size columns
        int columnCount = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);
        }

        // Add filter to the header row
        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H1"));

    }

}
