package com.TalabatSystem.Requests;

import com.TalabatSystem.Models.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
