package com.claro.moose.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Alenkart Rodriguez on 12/1/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogItem {

    long childId;
    String serviceType;
    String nameText;
    String itemType;
    String caption;
}
