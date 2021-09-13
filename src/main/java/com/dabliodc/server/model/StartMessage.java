package com.dabliodc.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartMessage {
    private String id;
    private String date;
    private String name;
    private String state;
    private String bravery;
    private String low;
}
